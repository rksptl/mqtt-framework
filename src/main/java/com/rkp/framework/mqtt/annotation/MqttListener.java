package com.rkp.framework.mqtt.annotation;

import com.rkp.framework.mqtt.model.Qos;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqttListener {

    /**
     * MQTT topic or topic filter.
     */
    String topic();

    /**
     * QoS level.
     */
    Qos qos() default Qos.AT_LEAST_ONCE;

    /**
     * Auto-start subscription.
     */
    boolean autoStartup() default true;

}
