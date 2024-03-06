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
});

let page_index = 1;
let page_total;
document.querySelector(".page-pre").addEventListener('click', () => {
    page_index--
    if (page_index < 1) {
        page_index = 1
    }
    query()
})
document.querySelector(".page-next").addEventListener('click', () => {
    page_index++;
    console.log('next')
    if (page_index > page_total) {
        page_index = page_total
    }
    query()
})

// 查询按钮
document.getElementById('query_car').addEventListener('click', () => {
    if (localStorage.getItem('username')) {
        document.querySelector('.login').classList.add('active')
        document.querySelector('.shield').classList.add('active')
    } else {
        query()
    }
})

function query() {
    const start = document.querySelector("input[name='start']");
    const end = document.querySelector("input[name='end']");
    $.ajax({
        method: "get",
        url: "/trainquery",
        dataType: "json",
        data: {
            start: start.value,
            end: end.value,
            page: page_index
        },
        success: function (result) {
            const {code, data, message} = result
            console.info(code)
            console.info(data)//数组
            console.info(message)
            if (result.code === 200) {
                let str = '';
                const endStationSet = new Set()
                for (let i = 0; i < data.length; i++) {

                    const {endStationid, startStationid, num, starttime, endtime} = data[i]
                    endStationSet.add(endStationid)
                    str += `
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
                const ck = document.createElement('input');
                ck.setAttribute('type', 'checkbox')
                ck.classList.add('ck')
                ck.innerHTML = result.data[0].startStationid
                // start_station.innerHTML = `
                //      <li>
                //     <input type="checkbox" name="" class="ck">${result.data[0].startStationid}
                // </li> `
                let endStr=''
                endStationSet.forEach((item) =>{
                //     endStr+= `
                //      <li>
                //     <input type="checkbox" name="" class="ck">${item}
                // </li>`
                    const ck = document.createElement('input');
                    ck.setAttribute('type', 'checkbox')
                    ck.classList.add('ck')
                    ck.innerHTML = item
                    ck.addEventListener()
                    end_station.append(ck)

                })
                // end_station.innerHTML =endStr


            } else if (data.code === 400) {

            }

        },
        error: function (data) {
            console.info(data)
        }
    })

    if (!page_total) {
        $.ajax({
            method: 'get', url: '/train_query_page', dataType: 'json', data: {
                start: start.value,
                end: end.value
            },
            success: function (data) {

                page_total = Math.ceil(data.data / 5)

                changePage()

            }, error: function (data) {

            }
        })
    } else {
        changePage()
    }
}

function changePage(){
    let str = ``;

    for (let i = 0; i < page_total; i++) {
        if (page_index === i + 1) {
            str += `<a href="javascript:;" class="page-index active">${i + 1}</a>`

        } else {
            str += `<a href="javascript:;" class="page-index">${i + 1}</a>`
        }

    }
    document.querySelector('.page').classList.add('active')
    document.querySelector('.page_wrapper').innerHTML = str
    console.log(page_total)
    console.log(page_index)
}

document.querySelector('.login .close i').addEventListener('click', () => {

    document.querySelector('.shield.active').classList.remove('active')
    document.querySelector('.login.active').classList.remove('active')

})
