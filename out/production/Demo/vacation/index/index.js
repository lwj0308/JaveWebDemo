const loginMenu = document.querySelector('.menu .menu-login');

function render() {
    if (sessionStorage.getItem('username')) {
        loginMenu.innerHTML = `
        您好，
        <a href="javascript:;">${sessionStorage.getItem('username')}</a>
        |
        <a href="javascript:;">退出</a>`

    } else{
        loginMenu.innerHTML = `
        <a href="javascript:;">登录</a>
        |
        <a href="javascript:;">注册</a>`
    }
}

render()

loginMenu.addEventListener('click',(ev)=>{
    switch (ev.target.innerHTML){
        case '登录':
            location.href = '../login/login.html'
            break
        case '注册':
            location.href = '../register/register.html.html'
            break
        case '退出':
            sessionStorage.removeItem('username')
            render()
            break
    }
   
})


document.querySelector('.sub-menu ul').addEventListener('click', (e) => {
    console.info(e.target.dataset.id)
    if (e.target.dataset.id === '0') {
        location.href = '../query/query.html'
    }
});


const img = document.querySelector('#banner-img img');
const circles = document.querySelectorAll('#selector span');
let index = 0;

function autoPlay() {
    setInterval(() => {
        index++
        if (index === 6) index = 0
        img.src = `../img/banner${index}.jpg`
        document.querySelector('span.active').classList.remove('active')
        circles[index].classList.add('active')
    }, 2000)
}

autoPlay()


