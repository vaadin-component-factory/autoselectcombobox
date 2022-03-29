import * as moment from 'moment/min/moment.min.js';

function parseCustomDate(dateString){
	var date = new Date();
	if (dateString.length <= 2){
		date.setDate(dateString);
	} else if (dateString.length <= 4){
		date.setDate(dateString.substring(0,2));
		date.setMonth(dateString.substring(2) - 1);
	}else if (dateString.length == 5){
		date.setDate(dateString.substring(0,2));
		date.setMonth(dateString.substring(2, 4) - 1);
		date.setYear("200" + dateString.substring(4));
	}else if (dateString.length == 6){
		date.setDate(dateString.substring(0,2));
		date.setMonth(dateString.substring(2, 4) - 1);
		date.setYear("20" + dateString.substring(4));
	}else if (dateString.length == 8){
		date.setDate(dateString.substring(0,2));
		date.setMonth(dateString.substring(2, 4) - 1);
		date.setYear(dateString.substring(4));
	}else {
		date = new Date(dateString);
	};
	return {
        day: date.getDate(),
        month: date.getMonth(),
        year: 1900 + date.getYear()
      }
}

window.setAKDBDateParser = (datePicker, pattern) => {
  requestAnimationFrame(() => {
	datePicker.set('i18n.formatDate', dateObject => moment(dateObject).format(pattern));
    datePicker.set('i18n.parseDate', dateString => {
    	let matchFormat = dateString.match(/[0-9]+\.[0-9]+\.[0-9]+/);
    	if (matchFormat){
    		const parsed = moment(dateString, pattern);
    		return {
    	        day: parsed.date(),
    	        month: parsed.month(),
    	        year: parsed.year()
    		}
    	} else {
    		return parseCustomDate(dateString);
    	}
    });
  });
}