// For inputDate4, where all input elements have class="date"

$(function() {
	$(".date").datetimepicker();
});

// Use coreservlets namespace to reduce chance of name conflicts

var coreservlets = {};

// Given "foo:bar:baz", returns "foo\:bar\:baz".
// For use in inputDate5, where each input element has its own id, but the id will contain ":"
// due to the JSF conventions. In jQuery, if you have <... id="foo:bar"/>, you cannot do 
// $("#foo:bar") because ":" has special meaning. So, you must do $("#foo\:bar") instead.

coreservlets.escapeColons = function(string) {
  return(string.replace(/:/g, "\\:"));
};

// Given "foo:date", returns "#foo\:date".
// That is, prepends "#" on the front, then escapes colons. 
// This yields an escaped version of the
// CSS id selector string that can be used by jQuery.

coreservlets.jQueryIdString = function(fieldId) {
  return("#" + coreservlets.escapeColons(fieldId));
};