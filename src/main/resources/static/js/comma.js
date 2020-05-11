function numberWithCommas() {
    price = document.querySelectorAll(".price");

    price.forEach(function(p){
        p.textContent = p.textContent.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + '원';
    });
}