function aplicaFiltroCards() {
    var input, filter, cards, cardContainer, h5, title, i;
    input = document.getElementById("filtroCards");
    filter = input.value.toUpperCase();
    cardContainer = document.getElementById("containerCards");
    cards = cardContainer.getElementsByClassName("col");
    for (i = 0; i < cards.length; i++) {
        title = cards[i].querySelector(".card-body");
        if (title.innerText.toUpperCase().indexOf(filter) > -1) {
            cards[i].style.display = "";
        } else {
            cards[i].style.display = "none";
        }
    }
}

//$(document).ready(function(){
//
//    $('#boton-modal').on('click', function(){
//        $('#modal-date').modal();
//    })
//
//    $( "#datepicker-inicial" ).datepicker({
//        format: "dd/mm/yyyy",
//        language: "pt-BR",
//        todayBtn: "linked",
//        clearBtn: true,
//        multidate: false,
//        todayHighlight: true
//    });
//    $( "#datepicker-final" ).datepicker({
//        format: "dd/mm/yyyy",
//        language: "pt-BR",
//        todayBtn: "linked",
//        clearBtn: true,
//        multidate: false,
//        todayHighlight: true
//    });
//})
$(function () {
    var checkout = $("#datepicker-final" ).datepicker({
        format: "dd/mm/yyyy",
        language: "pt-BR",
        todayBtn: "linked",
        clearBtn: true,
        multidate: false,
        todayHighlight: true,
        startDate: "today"
    });
    var checkin = $('#datepicker-inicial').datepicker({
        format: "dd/mm/yyyy",
        language: "pt-BR",
        todayBtn: "linked",
        clearBtn: true,
        multidate: false,
        todayHighlight: true,
        startDate: "today"
    }).on('changeDate', function(event) {
        var newDate = new Date(event.date)
        newDate.setDate(newDate.getDate() + 30)
        checkout.datepicker("setStartDate", event.date);
        checkout.datepicker("setEndDate", newDate);
    });
});


