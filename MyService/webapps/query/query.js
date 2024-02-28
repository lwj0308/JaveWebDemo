const ckAll = document.querySelectorAll('.ck_all');

ckAll.forEach(item => {
    const ul = item.nextElementSibling;
    const cks = ul.querySelectorAll('input');
    item.addEventListener('click', () => {
        item.classList.toggle('active')
        cks.forEach((item) => {
            item.checked = item.classList.toggle('active')
        })
    })
    cks.forEach((ck) => {
        ck.addEventListener('click', () => {

            if (ul.querySelectorAll('.ck:checked').length === cks.length) {
                item.classList.add('active')
            } else {
                item.classList.remove('active')
            }

        })
    })

})

const thead = document.querySelector('thead');

window.addEventListener('scroll', () => {
    if (document.documentElement.scrollTop >= thead.offsetTop) {
        // thead.style.position = 'fixed'
    }
})

document.querySelector('.login-txt li:first-child').addEventListener('click', () => {
    location.href = '../register/register.html'
})

document.querySelector('.login .tab ul').addEventListener('click', (e) => {
    if (e.target.tagName === 'A') {
        document.querySelector('.login .tab a.active').classList.remove('active')
        e.target.classList.add('active')

        document.querySelector('.login .login-bd.active').classList.remove('active')
        document.querySelectorAll('.login .login-bd')[+e.target.dataset.id].classList.add('active')
    }
})

document.getElementById('query_car').addEventListener('click', () => {
    if (localStorage.getItem('username')) {
        document.querySelector('.login').classList.add('active')
        document.querySelector('.shield').classList.add('active')
    } else {
        const train_query = document.querySelector(".train_query");
        const start = document.querySelector("input[name='start']");
        const end = document.querySelector("input[name='end']");
        $.ajax({
            method: "get",
            url: "/trainquery",
            dataType: "json",
            data: {
                start: start.value,
                end: end.value
            },
            success: function (result) {
                const {code ,data,message} = result
                console.info(code)
                console.info(data)//数组
                console.info(message)
                if (result.code === 200) {
                    let str='';
                    for (let i = 0;i<data.length;i++) {
                        console.info(data[i])
                        const {endStationid, startStationid, num,starttime,endtime} = data[i]
                        str+=`
    <tr>
            <td colspan="4">
                <div class="td_1">
                    <div>${num}</div>
                    <div>${startStationid}
                        <br>
                        ${endStationid}
                    </div>
                    <div>
                        <strong>${starttime}</strong>
                        <br>
                        ${endtime}
                    </div>
                    <div>
                        <strong>05:59</strong>
                        <br>
                        当日到达
                    </div>
                </div>
            </td>
            <td>
                候补
            </td>
            <td>
                候补
            </td>
            <td>
                候补
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                ---
            </td>
            <td>
                预订
            </td>

        </tr>
`;
                    }
                    const body = document.querySelector('.train_body');
                    const start_station = document.querySelector('.start_station');
                    const end_station = document.querySelector('.end_station');
                    body.innerHTML = str
                    start_station.innerHTML = `
                     <li>
                    <input type="checkbox" name="" class="ck">${result.data[0].startStationid}
                </li> `
                    end_station.innerHTML = `
                     <li>
                    <input type="checkbox" name="" class="ck">${result.data[0].endStationid}
                </li>`



                } else if (data.code === 400) {

                }

            },
            error: function (data) {
                console.info(data)
            }
        })

    }
})

document.querySelector('.login .close i').addEventListener('click', () => {

    document.querySelector('.shield.active').classList.remove('active')
    document.querySelector('.login.active').classList.remove('active')

})
