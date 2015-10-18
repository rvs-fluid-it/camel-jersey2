# camel-jersey2
Camel Jersey2 component so that Camel's Rest DSL can also be bound to the Jersey framework (version 2.x)

See [Camel Rest DSL](http://camel.apache.org/rest-dsl.html)

Usage:
------

```java
  ... 
  restConfiguration().component("jersey2");
  ...
```  

The jersey2-engine will be looked up from the Camel registry.   
It is basically what is needed when you use Camel inside of a Dropwizard application. Since the jersey-2 engine  will be instantiated by Dropwizard and should only passed to the CamelContext.
 
State:
------
Work in progress.
  
Planned for version 0.1-1:
* Http post
