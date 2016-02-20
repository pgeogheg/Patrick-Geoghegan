$(document).ready(function() {

	var MIN_PASS_LENGTH 	= 6;
	var INVALID_PASS_CHAR 	= " ! @ £ # $ % ^ & * ( ) { }";

	var ERROR_MSG_1 = "Password must be at least 6 characters long";
	var ERROR_MSG_2 = "Passwords cannot contain" + INVALID_PASS_CHAR;
	var ERROR_MSG_3 = "Passwords do not match";
	var ERROR_MSG_4 = "Invalid e-mail address";
	var ERROR_MSG_5 = "Invalid phone number";
	var ERROR_MSG_6 = "Field cannot be left blank";

	var keyPressed;

	var eventClickedId;

	console.log(this.title);
	var businessForm = document.forms["businessForm"];
	var formFields = [];
	for (var i = 0; i < businessForm.length; i++) {
		formFields.push(businessForm[i]['id']);
	}

	document.addEventListener("keydown", keyCheck);
	function keyCheck(event) {
		if (event.keyCode === 8) {
			keyPressed = 8;
			switch (eventClickedId) {
				case "passField":
					$.proxy(passFieldCheck, this)();
					break;
				case "confirmPassField":
					$.proxy(confirmPassFieldCheck, this)();
					break;
				case "emailField":
					$.proxy(emailFieldCheck, this)();
					break;
				case "phoneField":
					$.proxy(phoneFieldCheck, this)();
					break;
				case "cNameField":
				case "adminField":
				case "addressField":
					$.proxy(blankCheck, this)();
					break;
				default:
					break;
			}
		} else if (event.keyCode === 9) {
			if (formFields[(formFields.indexOf(eventClickedId) + 1)]){
				eventClickedId = formFields[(formFields.indexOf(eventClickedId) + 1)];
			} else {
				eventClickedId = "null";
			}
		}
	}

	$(document).click(function(event){
		if ($(event.target).attr('id')){
			eventClickedId = $(event.target).attr('id');
		} else {
			eventClickedId = "null";
		}
	});

	$("#passField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(passFieldCheck, this)();
	});

	$("#confirmPassField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(confirmPassFieldCheck, this)();
	});

	$("#emailField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(emailFieldCheck, this)();
	});

	$("#phoneField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(phoneFieldCheck, this)();
	});

	$("#cNameField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(blankCheck, this)();
	});

	$("#adminField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(blankCheck, this)();
	});

	$("#addressField").keypress(function() {
		keyPressed = event.keyCode;
		$.proxy(blankCheck, this)();
	});

	function blankCheck() {
		$("#" + eventClickedId + "DivErr").remove();
		var fieldVal = getValue();
		if (fieldVal === "") {
			$("#" + eventClickedId).after("<div id=\"" + eventClickedId + "DivErr\">" + ERROR_MSG_6 + "</div>");
			displayErrorField();
		} else {
			displayValidField();
		}
	};
	
	function emailFieldCheck() {
		var emailVal = getValue();
		$("#emailErrDiv").remove();
		if (emailVal === "") {
			$("#" + eventClickedId).after("<div id=\"emailErrDiv\">" + ERROR_MSG_6 + "</div>");
			displayErrorField();
			return;
		} else if (emailVal.indexOf('@') === -1 || emailVal.indexOf('.') === -1 ) {
			$("#" + eventClickedId).after("<div id=\"emailErrDiv\">" + ERROR_MSG_4 + "</div>");
			displayErrorField();
		} else {
			displayValidField();
		}
	};

	function phoneFieldCheck() {
		var phoneVal = getValue();
		$("#phoneErrDiv").remove();
		if (phoneVal === "") {
			$("#" + eventClickedId).after("<div id=\"phoneErrDiv\">" + ERROR_MSG_6 + "</div>");
			displayErrorField();
			return;
		} else if (isNaN(phoneVal.split(' ').join(''))) {
			$("#" + eventClickedId).after("<div id=\"phoneErrDiv\">" + ERROR_MSG_5 + "</div>");
			displayErrorField();
		} else {
			displayValidField();
		}
	}

	function passFieldCheck() {
		var passVal = getValue();
		var passLength = passVal.length;
		$("#passErrDiv").remove();
		if (passVal === "") {
			$("#" + eventClickedId).after("<div id=\"passErrDiv\">" + ERROR_MSG_6 + "</div>");
			displayErrorField();
			return;
		}
		for (var i = 0; i < INVALID_PASS_CHAR.length; i++) {
			if (passVal.indexOf(INVALID_PASS_CHAR[i * 2]) > -1 ) {
				$("#" + eventClickedId).after("<div id=\"passErrDiv\">" + ERROR_MSG_2 + "</div>");
				displayErrorField();
				return;
			}
		}
		if (passLength <= 5) {
			displayErrorField();
			$("#" + eventClickedId).after("<div id=\"passErrDiv\">" + ERROR_MSG_1 + "</div>");
		} else {
			displayValidField();
		}
	};

	function confirmPassFieldCheck() {
		$("#confErrDiv").remove();
		var passVal = $("#passField").val();
		var confirmPassVal = getValue();
		if (confirmPassVal === "") {
			$("#" + eventClickedId).after("<div id=\"confErrDiv\">" + ERROR_MSG_6 + "</div>");
			displayErrorField();
			return;
		} else if (passVal !== confirmPassVal) {
			displayErrorField();
			$("#" + eventClickedId).after("<div id=\"confErrDiv\">" + ERROR_MSG_3 + "</div>");
		} else {
			displayValidField();
		}
	};

	function displayErrorField() {
		$("#" + eventClickedId).css("border-color", "#FF0000");
		$("#" + eventClickedId).css("border-style", "solid");
		$("#" + eventClickedId).css("border-width", "5px");
	};

	function displayValidField() {
		$("#" + eventClickedId).css("border-color", "#00FF00");
		$("#" + eventClickedId).css("border-style", "#FF0000");
		$("#" + eventClickedId).css("border-width", "5px");
	};

	function getValue() {
		if (keyPressed === 8) {
			return $("#" + eventClickedId).val().substring(0, $("#" + eventClickedId).val().length - 1);
		} else {
			return $("#" + eventClickedId).val() + String.fromCharCode(event.keyCode);
		}
	};
});

function store_business_info() {
	var data = [];
	var errorMsg = "";
	data.push(document.forms["businessForm"]["cName"].value); 		// 0
	data.push(document.forms["businessForm"]["email"].value); 		// 1
	data.push(document.forms["businessForm"]["phone"].value); 		// 2
	data.push(document.forms["businessForm"]["admin"].value); 		// 3
	data.push(document.forms["businessForm"]["pass"].value); 		// 4
	data.push(document.forms["businessForm"]["confirmPass"].value); // 5
	data.push(document.forms["businessForm"]["address"].value); 	// 6
	data.push(document.forms["businessForm"]["address2"].value); 	// 7
	data.push(document.forms["businessForm"]["industry"].value); 	// 8
	data.push(document.forms["businessForm"]["bio"].value); 		// 9
	console.log(data);
};