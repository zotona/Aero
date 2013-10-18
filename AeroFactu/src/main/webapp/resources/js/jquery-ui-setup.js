$(function() {
	$(".date").datepicker();
});

var coreservlets = {};

coreservlets.escapeColons = function(string) {
  return(string.replace(/:/g, "\\:"));
};

coreservlets.jQueryIdString = function(fieldId) {
  return("#" + coreservlets.escapeColons(fieldId));
};