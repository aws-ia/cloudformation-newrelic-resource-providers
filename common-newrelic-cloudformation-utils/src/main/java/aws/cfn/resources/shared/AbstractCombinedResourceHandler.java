package aws.cfn.resources.shared;

import org.apache.commons.lang3.function.FailableRunnable;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.proxy.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractCombinedResourceHandler<
        This extends AbstractCombinedResourceHandler<This, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT>,
        ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT>
        implements HandlerMixins<ResourceModelT, CallbackContextT> {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AbstractCombinedResourceHandler.class);

    protected ResourceHandlerRequest<ResourceModelT> request;
    protected CallbackContextT callbackContext;
    protected Logger logger;
    protected TypeConfigurationModelT typeConfiguration;
    protected ResourceModelT model;

    protected ProgressEvent<ResourceModelT, CallbackContextT> result = null;

    public interface BaseHandlerAdapterDefault<This extends AbstractCombinedResourceHandler<This,?,?, ResourceModel, CallbackContext, TypeConfigurationModel>, ResourceModel, CallbackContext, TypeConfigurationModel> {
        This newCombinedHandler();

        default ProgressEvent<ResourceModel, CallbackContext> handleRequest(AmazonWebServicesClientProxy proxy, ResourceHandlerRequest<ResourceModel> request, CallbackContext callbackContext, Logger logger, TypeConfigurationModel typeConfiguration) {
            return newCombinedHandler().init(proxy, request, callbackContext, logger, typeConfiguration).applyActionForHandlerClass(getClass());
        }
    }

    @SuppressWarnings("unchecked")
    public This init(
            AmazonWebServicesClientProxy proxy,
            ResourceHandlerRequest<ResourceModelT> request,
            CallbackContextT callbackContext,
            Logger logger,
            TypeConfigurationModelT typeConfiguration) {

        this.request = request;
        this.callbackContext = callbackContext;
        this.logger = logger;
        this.typeConfiguration = typeConfiguration;
        // TODO should be more careful between "previous" and "desired"
        this.model = request.getDesiredResourceState();
        // TODO need to capture -- request.getPreviousResourceState() -- and use it in places?  e.g. merge them
        /*
        eg:

          when creating ProjectToken

            we set the ApiToken into the state

          on a subsequent update from the user

            ApiToken will be in PreviousState but probably not in DesiredState
            should it be in the result?

          is this also a problem with fields like ID?
          (masked by the fact that we regenerate)
         */


        try {
            doInit();

        } catch (Exception e) {
            // only available when run locally
            LOG.error("Error in request init: "+e);
            LOG.debug("Trace for error: "+e, e);
            logger.log("Error in request init: "+toStackTrace(e));

            if (e instanceof FailureToSetInResult) {
                result = (ProgressEvent<ResourceModelT, CallbackContextT>) ((FailureToSetInResult)e).getResult();
            } else {
                result = failure(e);
            }
        }

        return (This) this;
    }

    public ResourceModelT getModel() { return model; }

    protected static String toStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    private final Map<String,Supplier<ProgressEvent<ResourceModelT, CallbackContextT>>> actionClazzNames = new LinkedHashMap<>();
    {
        actionClazzNames.put("CreateHandler", this::safeCreate);
        actionClazzNames.put("ReadHandler", this::safeRead);
        actionClazzNames.put("UpdateHandler", this::safeUpdate);
        actionClazzNames.put("DeleteHandler", this::safeDelete);
        actionClazzNames.put("ListHandler", this::safeList);
    }
    public ProgressEvent<ResourceModelT, CallbackContextT> applyActionForHandlerClass(Class<?> clazz) {
        return actionClazzNames.get(clazz.getSimpleName()).get();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Throwable> ProgressEvent<ResourceModelT, CallbackContextT> safely(FailableRunnable<T> task) {
        if (result!=null) return result;
        try {
            task.run();
            if (result==null) result = success();

        } catch (Throwable e) {
            // only available when run locally
            LOG.warn("Error in request: "+e);
            LOG.debug("Trace for error: "+e, e);
            logger.log("Error in request: "+toStackTrace(e));

            if (e instanceof FailureToSetInResult) {
                result = (ProgressEvent<ResourceModelT, CallbackContextT>) ((FailureToSetInResult)e).getResult();
            } else {
                result = failure(e);
            }
        }

        return result;
    }

    public ProgressEvent<ResourceModelT, CallbackContextT> safeCreate() { return safely(this::create); }
    public ProgressEvent<ResourceModelT, CallbackContextT> safeRead() { return safely(this::read); }
    public ProgressEvent<ResourceModelT, CallbackContextT> safeUpdate() { return safely(this::update); }
    public ProgressEvent<ResourceModelT, CallbackContextT> safeDelete() { return safely(this::delete); }
    public ProgressEvent<ResourceModelT, CallbackContextT> safeList() { return safely(this::list); }

    protected void doInit() throws Exception {
        // nothing here, but can be overridden
    }

    protected void create() throws Exception { newHelper().create(); }
    protected void read() throws Exception { newHelper().read(); };
    protected void update() throws Exception { newHelper().update(); };
    protected void delete() throws Exception { newHelper().delete(); };
    protected void list() throws Exception { newHelper().list(); };

    public abstract Helper newHelper();

    public abstract class Helper {
        public abstract IdT getId(ResourceModelT model);
        public final Optional<ItemT> findExistingItemMatchingModel() throws Exception {
            if (model==null) return Optional.empty();
            return findExistingItemWithId(getId(model));
        }

        public final Optional<ItemT> findExistingItemWithId(IdT id) throws Exception {
            if (id==null) return Optional.empty();
            return findExistingItemWithNonNullId(id);
        }
        protected abstract Optional<ItemT> findExistingItemWithNonNullId(IdT id) throws Exception;

        protected Optional<ItemT> findExistingItemWithIdDefaultInefficiently(IdT id) throws Exception {
            return readExistingItems().stream().filter(item -> Objects.equals(id, getId(modelFromItem(item)))).findAny();
        }

        public abstract List<ItemT> readExistingItems() throws Exception;
        public abstract ResourceModelT modelFromItem(ItemT item);
        public abstract ItemT createItem() throws Exception;
        public abstract void updateItem(ItemT item, List<String> updates) throws Exception;
        public abstract void deleteItem(ItemT item) throws Exception;

        public void create() throws Exception {
            Optional<ItemT> item = findExistingItemMatchingModel();
            if (item.isPresent()) fail(HandlerErrorCode.AlreadyExists, "Resource already exists.");
            model = modelFromItem(createItem());
        }

        public void read() throws Exception {
            Optional<ItemT> item = findExistingItemMatchingModel();
            if (!item.isPresent()) failNotFound();
            model = modelFromItem(item.get());
        }

        public void update() throws Exception {
            Optional<ItemT> item = findExistingItemMatchingModel();
            List<String> updates = new ArrayList<>();
            if (result == null || !result.isInProgress()) {
                if (!item.isPresent()) failNotFound();
                updateItem(item.get(), updates);
            }
            if (!updates.isEmpty()) item = findExistingItemMatchingModel();
            ResourceModelT newModel = (item == null || !item.isPresent()) ? null : modelFromItem(item.get());
            if (!model.equals(newModel)) {
                result = inProgress();
            } else {
                model = modelFromItem(item.get());
                result = success(updates.isEmpty()
                        ? "No changes"
                        : "Changes: "+updates);
            }
        }

        public void delete() throws Exception {
            if (result == null || !result.isInProgress()) {
                Optional<ItemT> item = findExistingItemMatchingModel();
                if (!item.isPresent()) {
                    result = ProgressEvent.<ResourceModelT, CallbackContextT>builder()
                            .status(OperationStatus.FAILED)
                            .errorCode(HandlerErrorCode.NotFound)
                            .build();
                    return;
                }
                deleteItem(item.get());
            }
            Optional<ItemT> deletedItem = findExistingItemMatchingModel();
            if (deletedItem.isPresent()) {
                result = inProgress();
            } else {
                result = ProgressEvent.<ResourceModelT, CallbackContextT>builder()
                        .status(OperationStatus.SUCCESS)
                        .build();
                model = null;
            }
        }

        public void list() throws Exception {
            List<ResourceModelT> models = readExistingItems().stream().map(this::modelFromItem).collect(Collectors.toList());
            result = ProgressEvent.<ResourceModelT, CallbackContextT>builder()
                    .resourceModels(models)
                    .status(OperationStatus.SUCCESS)
                    .build();
        }
    }
}
