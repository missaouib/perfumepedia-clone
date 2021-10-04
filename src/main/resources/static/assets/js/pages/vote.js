$(document).ready(function(){

    $("input[name='loveradio']").click(function() {
        var loveradio = $("input[name='loveradio']:checked").val();
        var idProduct = $("#idproduct").html();
        console.log(loveradio);
        console.log(idProduct);
        $.ajax({
            async: true,
            type: "post",
            url: "/vote/product",
            dataType: 'json',
            data: {
                voteValue : loveradio,
                idProduct : idProduct
            },
            success: function(response) {
                if(response.status == "Done") {
                    console.log('Działa');
                    $("#ilovequantity").html(response.data.loveQuantity);
                    $("#ilikequantity").html(response.data.likeQuantity);
                    $("#idislikequantity").html(response.data.disLikeQuantity);
                    $("#successMessage").fadeIn('xfast', function(){
                        $("#successMessage").delay(3000).fadeOut();
                    });
                }
                if(response.status == "not-login") {
                    $("#errorMessage").fadeIn('xfast', function(){
                        $("#errorMessage").delay(3000).fadeOut();
                    });
                }
            },
            error: function(response) {
                console.log(response.status)
                console.log('nie działa');
            },
        });
    })

    $('input[name="have"], input[name="season"], input[name="daynight"]').change(
        function () {
            var idProduct = $("#idproduct").html();
            if ($(this).is(':checked')) {
                var voteName = $(this).val();
                var voteValue = 1;
            } else if ($(this).not(':checked')) {
    		    var voteName = $(this).val();
    			var voteValue = 0;
    		}
    		console.log(voteName)
    		console.log(voteValue)
    		console.log(idProduct)
    		$.ajax ({
    		url: "/vote/productdetail",
    		type: "POST",
    		data: {
    		    voteName: voteName,
    		    voteValue: voteValue,
    		    idProduct: idProduct
    		},
    		dataType: 'json',
    		success: function(data) {
    		    if (data.status) {
    		        if ((voteName == 'ihave') || (voteName == 'ihad') || (voteName == 'iwant')) {
    				    $('#successMessage2').fadeIn('xfast', function(){
    					    $('#successMessage2').delay(1000).fadeOut();
    				    });

    				}
    				if ((voteName == 'winter') || (voteName == 'spring') || (voteName == 'summer') || (voteName == 'autumn')) {
    				    $('#successMessage3').fadeIn('xfast', function(){
    					    $('#successMessage3').delay(1000).fadeOut();
    				    });
    			    }
    				if ((voteName == 'day') || (voteName == 'night')) {
    				    $('#successMessage4').fadeIn('xfast', function(){
    					    $('#successMessage4').delay(1000).fadeOut();
    					});
    				}
                }
    		},
    		error: function() {
    			if ((voteName == 'ihave') || (voteName == 'ihad') || (voteName == 'iwant')) {
    			    $('#falseMessage2').fadeIn('xfast', function(){
    				    $('#falseMessage2').delay(1000).fadeOut();
    				});
    			}
                if ((voteName == 'winter') || (voteName == 'spring') || (voteName == '$summer') || (voteName == 'autumn')) {
                    $('#falseMessage3').fadeIn('xfast', function(){
                        $('#falseMessage3').delay(1000).fadeOut();
                    });
                }
                if ((voteName == 'day') || (voteName == 'night')) {
                    $('#falseMessage4').fadeIn('xfast', function(){
                        $('#falseMessage4').delay(1000).fadeOut();
                    });
                }
    		},
    	    });
    	});
});
