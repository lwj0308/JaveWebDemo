let img = document.querySelector('img');
let span = document.querySelectorAll('span');
let index = 0;
let timer;
function show() {
    img.src = `img/banner${index}.jpg`;
}

function autoPlay() {
    timer = setInterval(function () {
        if (index === span.length) {
            index = 0;
        }
        show();
        index++;
    }, 1000);
}
autoPlay();

function ctrlPlay() {
    for (let i = 0; i < span.length; i++) {
        span[i].onclick = function () {
            index = i;
            show();
        }
    }
}
ctrlPlay();

function eventList() {
    for (let i = 0; i < span.length; i++) {
        span[i].addEventListener('mouseenter', function () {
            clearInterval(timer);
            index = i;
            show();
        }, false);
        span[i].addEventListener('mousemove', function () {
            clearInterval(timer);
            autoPlay();
        }, false);

    }
}
eventList();