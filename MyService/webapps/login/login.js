let loginBtn = document.querySelector('.login-btn')
let inputs = document.querySelectorAll('.wrapper input')

const list = document.querySelector('.banner-list');
const li1 = list.children[0].cloneNode(true);

const ols = document.querySelectorAll('.banner-wrapper ol li');
list.appendChild(li1)
let timer;
let index = 0;

function autoPlay() {
    timer = setInterval(() => {
        index++
        list.style.transition = '0.5s ease'
        const offset = -index * 1473;
        list.style.transform = `translateX(${offset}px)`;
        if (index === 4) {
            index = 0
            setTimeout(() => {
                list.style.transition = 'none'
                list.style.transform = `translateX(${0})`;

            }, 500)
        }
        document.querySelector('.banner-wrapper ol .active').classList.remove('active')
        document.querySelector(`.banner-wrapper ol li:nth-child(${index + 1})`).classList.add('active')


    }, 2000)
}

autoPlay()
loginBtn.addEventListener('click', (ev) => {
    ev.preventDefault()
    if (inputs[0].value && inputs[1].value) {
        sessionStorage.setItem('username', inputs[0].value)
        // location.href = '../index.html'
        // let form = document.querySelector('form');
        // form.submit()
        $.ajax({
            method: "get",
            url: "/loginIndex",
            dataType: "text",
            data: {
                acc: inputs[0].value,
                pwd: inputs[1].value
            },
            success: response => {
                if (response=== '登录成功'){
                    location.href = '../index.html'
                } else {
                    alert("登录失败，请检查账号密码")
                }

                console.log(response)
            },
            error: error => {
                console.log(error)
            }
        })
    } else {
        document.querySelector('.login .login-error').classList.add('active')
        // alert('账户、密码不能为空')

    }

})

document.querySelector('.login-txt li:first-child').addEventListener('click', () => {
    location.href = '../register/register.html'
})

document.querySelector('.login .hd ul').addEventListener('click', (e) => {
    if (e.target.tagName === 'A') {
        document.querySelector('.login .hd a.active').classList.remove('active')
        e.target.classList.add('active')

        document.querySelector('.login .login-bd.active').classList.remove('active')
        document.querySelectorAll('.login .login-bd')[+e.target.dataset.id].classList.add('active')
    }
})



