goog.addDependency("base.js", ['goog'], []);
goog.addDependency("../cljs/core.js", ['cljs.core'], ['goog.string', 'goog.array', 'goog.object', 'goog.string.StringBuffer']);
goog.addDependency("../om/dom.js", ['om.dom'], ['cljs.core']);
goog.addDependency("../om/core.js", ['om.core'], ['cljs.core', 'om.dom']);
goog.addDependency("../kernel_time/core.js", ['kernel_time.core'], ['cljs.core', 'goog.net.EventType', 'om.core', 'om.dom', 'goog.net.XhrIo', 'goog.events', 'goog.Uri', 'goog.async.Deferred']);