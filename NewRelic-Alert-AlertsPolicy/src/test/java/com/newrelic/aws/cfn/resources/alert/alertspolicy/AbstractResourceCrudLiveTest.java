package com.newrelic.aws.cfn.resources.alert.alertspolicy;

import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler;
import com.gitlab.aws.cfn.resources.shared.AbstractCombinedResourceHandler.BaseHandlerAdapterDefault;
import org.assertj.core.api.AbstractComparableAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.cloudformation.AbstractWrapper;
import software.amazon.cloudformation.Action;
import software.amazon.cloudformation.LambdaWrapper;
import software.amazon.cloudformation.loggers.JavaLogPublisher;
import software.amazon.cloudformation.loggers.LogFilter;
import software.amazon.cloudformation.proxy.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith({MockitoExtension.class})
@TestMethodOrder(OrderAnnotation.class)
@Tag("Live")
public abstract class AbstractResourceCrudLiveTest<CombinedHandlerT extends AbstractCombinedResourceHandler<CombinedHandlerT, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT>, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractResourceCrudLiveTest.class);
    @Mock
    protected AmazonWebServicesClientProxy proxy;
    @Mock
    protected software.amazon.cloudformation.proxy.Logger logger;
    protected TypeConfigurationModelT typeConfiguration;
    protected ResourceModelT model;
    protected final String TEST_ID = UUID.randomUUID().toString();

    public AbstractResourceCrudLiveTest() {
    }

    protected ResourceHandlerRequest<ResourceModelT> newRequestObject() {
        return ResourceHandlerRequest.<ResourceModelT>builder().desiredResourceState(this.model).build();
    }

    protected abstract TypeConfigurationModelT newTypeConfiguration() throws Exception;

    protected abstract ResourceModelT newModelForCreate() throws Exception;

    protected abstract ResourceModelT newModelForUpdate() throws Exception;

    protected abstract LambdaWrapper<ResourceModelT, CallbackContextT, TypeConfigurationModelT> newHandlerWrapper();

    protected LambdaWrapper<ResourceModelT, CallbackContextT, TypeConfigurationModelT> newHandlerWrapperInitialized() {
        LambdaWrapper<ResourceModelT, CallbackContextT, TypeConfigurationModelT> wrapper = this.newHandlerWrapper();

        try {
            Field lp = AbstractWrapper.class.getDeclaredField("loggerProxy");
            lp.setAccessible(true);
            LoggerProxy lpr = new LoggerProxy();
            lpr.addLogPublisher(new JavaLogPublisher(LOG, new LogFilter[0]));
            lp.set(wrapper, lpr);
            return wrapper;
        } catch (IllegalAccessException | NoSuchFieldException var4) {
            throw new RuntimeException(var4);
        }
    }

    protected ProgressEvent<ResourceModelT, CallbackContextT> invoke(Action action) throws Exception {
        return this.newHandlerWrapperInitialized().invokeHandler(this.proxy, this.newRequestObject(), action, null, this.typeConfiguration);
    }

    @SuppressWarnings("unchecked")
    protected AbstractCombinedResourceHandler<CombinedHandlerT, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT>.Helper newHandlerHelper() {
        try {
            Object hw = this.newHandlerWrapper();
            Field f = hw.getClass().getDeclaredField("handlers");
            f.setAccessible(true);
            AbstractCombinedResourceHandler<CombinedHandlerT, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT> combined = ((BaseHandlerAdapterDefault)(((Map)f.get(hw)).values().iterator().next())).newCombinedHandler();
            combined.init(this.proxy, this.newRequestObject(), null, this.logger, this.newTypeConfiguration());
            return combined.newHelper();
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    protected final ItemT getRealItem() throws Exception {
        AbstractCombinedResourceHandler<CombinedHandlerT, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT>.Helper helper = this.newHandlerHelper();
        return helper.findExistingItemWithId(helper.getId(this.model)).get();
    }

    protected final Optional<ItemT> getRealItem(ItemT item) throws Exception {
        AbstractCombinedResourceHandler<CombinedHandlerT, ItemT, IdT, ResourceModelT, CallbackContextT, TypeConfigurationModelT>.Helper helper = this.newHandlerHelper();
        return helper.findExistingItemWithId(helper.getId(helper.modelFromItem(item)));
    }

    @BeforeAll
    public void initTestHandlerItems() throws Exception {
        this.proxy = Mockito.mock(AmazonWebServicesClientProxy.class);
        this.logger = Mockito.mock(software.amazon.cloudformation.proxy.Logger.class);
        this.typeConfiguration = this.newTypeConfiguration();
    }

    @Test
    @Order(10)
    @SuppressWarnings("unchecked")
    public void testCreate() throws Exception {
        this.model = this.newModelForCreate();
        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.CREATE);
        Assertions.assertThat(response).isNotNull();
        this.assertStatusSuccess(response);
        Assertions.assertThat(response.getCallbackContext()).isNull();
        Assertions.assertThat(response.getCallbackDelaySeconds()).isEqualTo(0);
        Assertions.assertThat(response.getErrorCode()).isNull();
        this.model = response.getResourceModel();
        Assertions.assertThat(this.model).isNotNull();
        ((ObjectAssert)Assertions.assertThat(this.getRealItem()).isNotNull()).matches((item) -> {
            return this.model.equals(this.newHandlerHelper().modelFromItem((ItemT) item));
        });
    }

    @Test
    @Order(20)
    public void testRead() throws Exception {
        if (this.model == null) {
            Assertions.fail("Create test must succeed for this to be meaningful.");
        }

        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.READ);
        Assertions.assertThat(response).isNotNull();
        this.assertStatusSuccess(response);
        Assertions.assertThat(response.getResourceModel()).isEqualTo(this.model);
    }

//    @SuppressWarnings("raw")
    protected void assertStatusSuccess(ProgressEvent<ResourceModelT, CallbackContextT> response) {
        ((AbstractComparableAssert)Assertions.assertThat(response.getStatus()).describedAs("Response: code %s, message %s.", new Object[]{response.getErrorCode(), response.getMessage()})).isEqualTo(OperationStatus.SUCCESS);
    }

    @Test
    @Order(30)
    public void testListAfterCreate() throws Exception {
        if (this.model == null) {
            Assertions.fail("Create test must succeed for this to be meaningful.");
        }

        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.LIST);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        Assertions.assertThat(response.getResourceModels()).anyMatch((m) -> {
            return m.equals(this.model);
        });
    }

    @Test
    @Order(40)
    public void testUpdateWithoutChange() throws Exception {
        if (this.model == null) {
            Assertions.fail("Create test must succeed for this to be meaningful.");
        }

        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.UPDATE);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        Assertions.assertThat(response.getResourceModel()).isEqualTo(this.model);
    }

    @Test
    @Order(50)
    public void testUpdateWithChange() throws Exception {
        this.model = this.newModelForUpdate();
        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.UPDATE);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        Assertions.assertThat(response.getResourceModel()).isEqualTo(this.model);
    }


    @Test
    @Order(60)
    public void testListAfterUpdate() throws Exception {
        if (this.model == null) {
            Assertions.fail("Create test must succeed for this to be meaningful.");
        }

        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.LIST);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        Assertions.assertThat(response.getResourceModels()).anyMatch((m) -> {
            return m.equals(this.model);
        });
    }

    @Test
    @Order(70)
    public void testDelete() throws Exception {
        if (this.model == null) {
            Assertions.fail("Create test must succeed for this to be meaningful.");
        }

        ItemT oldItem = this.getRealItem();
        ProgressEvent<ResourceModelT, CallbackContextT> response = this.invoke(Action.DELETE);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatus()).isEqualTo(OperationStatus.SUCCESS);
        Assertions.assertThat(response.getResourceModel()).isNull();
        this.assertDelete(oldItem);
    }

    protected void assertDelete(ItemT oldItem) throws Exception {
        Assertions.assertThat(this.getRealItem(oldItem)).isNotPresent();
    }

    @AfterAll
    public void tearDown() {
    }
}
