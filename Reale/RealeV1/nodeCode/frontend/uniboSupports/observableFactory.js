/*
frontend/uniboSupports/observableFactory.js
=================================================================================================================
See https://www.monterail.com/blog/2016/how-to-build-a-reactive-engine-in-javascript-part-1-observable-objects

The code lacks the support for non-primitive data types or nested properties 
and many of the required sanity checks, thus by no means should this code be considered production ready. 

The code below is written using the ES2015 standard and is loosely inspired by the Vue.js 
reactive engine implementation.
=================================================================================================================
*/

//factory function
observable = function(dataObj) {
  let signals = {};

  observeData(dataObj);

  // Besides the reactive data object, we also want to return and thus expose the observe and notify functions.
  return {
    data: dataObj,
    observe,
    notify
  }


  function observe (property, signalHandler) {
  // If there is NO signal for the given property, 
  // we create it and set it to a new array to store the signalHandlers   
    if(!signals[property]) signals[property] = []

  //We push the signalHandler into the signal array, 
  //which effectively gives us an array of callback functions
    signals[property].push(signalHandler)
  }

  function notify (signal) {
  //Early return if there are no signal handlers
    if(!signals[signal] || signals[signal].length < 1) return
  
  //We call each signalHandler that�s observing the given property
    signals[signal].forEach((signalHandler) => signalHandler())
  }

 /*
 ---------------------------------------------------------------------
 makeReactive, observeData
 	the getters/setters way of observing and reacting to changes
 	two functions that will transform our object�s properties into
  	observable properties using the getter/setter functionality.
 ---------------------------------------------------------------------
 */
  function makeReactive (obj, key) {
    let val = obj[key]

    Object.defineProperty(obj, key, {
      get () {
        return val
      },
      set (newVal) {
        val = newVal
        notify(key)
      }
    })
  }

  function observeData (obj) {
    for (let key in obj) {
      if (obj.hasOwnProperty(key)) {
        makeReactive(obj, key)
      }
    }
  }
};

/*
//USAGE
Object.observe(what,callback) = function(){
	App = new Observable(self);
	App.observe.
}
*/

module.exports = observable; 