document.addEventListener('DOMContentLoaded', function () {
   
    document.getElementById('abrirCamisas').addEventListener('click', function (event) {
        event.preventDefault(); // Evita que el enlace recargue la página
        
      
        document.getElementById('tab1').checked = true; 
        
     
        document.querySelector('#tab1').scrollIntoView({
            behavior: 'smooth'
        });
    });
});

var swiper1 = new Swiper(".mySwiper-1", { 
    slidesPerView: 1,
    spaceBetween: 30,
    loop: true,
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    }
});

var swiper2 = new Swiper(".mySwiper-2", { 
    slidesPerView: 3,
    spaceBetween: 20,
    loop: true,
    loopFillGroupWithBlank: true,
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    breakpoints: {
        0: {
            slidesPerView: 1,
        },
        520: {
            slidesPerView: 2,
        },
        950: {
            slidesPerView: 3,
        },
    }
});

let tabInputs = document.querySelectorAll(".tabInput");

tabInputs.forEach(function(input) {
    input.addEventListener("change", function() {
        let id = input.id; 
        let thisSwiper = document.getElementById("swiper-" + id); 
        if (thisSwiper && thisSwiper.swiper) {
            thisSwiper.swiper.update(); 
        }
    });
});
