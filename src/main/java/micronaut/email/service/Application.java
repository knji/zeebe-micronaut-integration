package micronaut.email.service;

import io.micronaut.runtime.Micronaut;


public class Application {

    public static void main(String[] args) {


        Micronaut.run(Application.class);

        //ZeebeClient client = setupOrderProcessingOrchestration();
        //BeanContext.run().registerSingleton(ZeebeClient.class, client);

        //BeanContext.build().createBean(ZeebeClient.class);
        //BeanContext.build().registerSingleton(ZeebeClient.class, client);


    }



}