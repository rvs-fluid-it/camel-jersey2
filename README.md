# camel-jersey2
[![Build Status](https://api.travis-ci.org/rvs-fluid-it/camel-jersey2.svg)](https://travis-ci.org/rvs-fluid-it/camel-jersey2)
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
It is basically what is needed when you use Camel from within a Dropwizard application. The jersey-2 engine  will allready be instantiated by Dropwizard and hence should only be propagated to the CamelContext.
 
State:
------
Work in progress.
  
Planned for version 0.1-1:
* Http post
* Http get

Credits:
--------
The technical design and the code is heavily influenced by the Camel Restlet component. 
