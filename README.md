# zeebe-micronaut-integration
Integrates zeebe with micronaut services

A repository to help users understand Zeebe as well as the Micronaut framework, two promising open source projects.  
The goal is to start with a simple use case and gradually build up complexity.

What we have so far is a simple order processing service with tasks to collect money, fetch items and ship including a 
parrallel task to send a customer email notification.

It works like this:

Service starts up on port 8085: 
Service exposes an endpoint to post a customer order.  Format is:


{ 
    "item" : "headphones",
    "numberOfItems" : 2,
    "price" : 200,
    "paymentMethod" : "VISA",
    "accountNumber" : 4567
}

OrderController responses to POST by creating an OrderCommand and submitting this to a workflow order-process which has 
been started by the Zeebe client for Java.
