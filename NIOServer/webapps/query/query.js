const ckAll = document.querySelectorAll('.ck_all');

//全部车站
ckAll.forEach(item => {
    const ul = item.nextElementSibling;

    item.addEventListener('click', () => {
        item.classList.toggle('active')
        const cks = ul.querySelectorAll('input');
        cks.forEach((item) => {
            item.checked = item.classList.toggle('active')
        })
    })

    // cks.forEach((ck) => {
    //     ck.addEventListener('click', () => {
    //
    //         if (ul.querySelectorAll('.ck:checked').length === cks.length) {
    //             item.classList.add('active')
    //         } else {
    //             item.classList.remove('active')
    //         }
    //
    //     })
    // })

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

let currentPageIndex = 1;
let page_total;
document.querySelector(".page-pre").addEventListener('click', () => {
    currentPageIndex--
    if (currentPageIndex < 1) {
        currentPageIndex = 1
    }
    query()
})
document.querySelector(".page-next").addEventListener('click', () => {
    currentPageIndex++;
    console.log('next')
    if (currentPageIndex > page_total) {
        currentPageIndex = page_total
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

function query(startStation, endStation) {
    let start = '';
    let end = '';


    if (!startStation) {
        start = document.querySelector("input[name='start']");
    }
    if (!endStation) {
        end = document.querySelector("input[name='end']");
    }

    $.ajax({
        method: "get",
        url: "/trainquery",
        dataType: "json",
        data: {
            start: start.value,
            end: end.value,
            page: currentPageIndex
        },
        success: function (result) {
            const {code, data, message} = result
            console.info(code)
            console.info(data)//对象page
            console.info(message)
            if (result.code === 200) {
                let str = '';
                const startStationSet = new Set()//车站
                const endStationSet = new Set()//车站

                const {pageCount, pageSize, item} = data
                page_total = pageCount
                for (let i = 0; i < item.length; i++) {

                    const {endStationid, startStationid, num, starttime, endtime} = item[i]
                    endStationSet.add(endStationid)
                    startStationSet.add(startStationid)
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
                document.querySelector('.train_body').innerHTML = str
                //处理车站
                const start_station = document.querySelector('.start_station');
                const end_station = document.querySelector('.end_station');

                let startStationText = "";
                startStationSet.forEach((item) => {
                    startStationText += `
                         <li>
                        <input type="checkbox" name="" class="ck" onclick="ckStartStationClick(1,this)" value=${item} >${item}
                    </li>`
                })
                start_station.innerHTML = startStationText
                let endStationText = "";
                endStationSet.forEach((item) => {
                    endStationText += `
                         <li>
                        <input type="checkbox" name="" class="ck" onclick="ckStartStationClick(2,this)" value=${item}>${item}
                    </li>`
                })
                end_station.innerHTML = endStationText
                //分页导航
                changePage(pageCount)

            } else if (data.code === 400) {

            }

        },
        error: function (data) {
            console.info(data)
        }
    })

}

//车站点击事件
function ckStartStationClick(startOrEnd, obj) {
    const ul = ckAll[startOrEnd].nextElementSibling;
    if (ul.querySelectorAll('.ck:checked').length === ul.children.length) {
        ckAll[startOrEnd].classList.add('active')
    } else {
        ckAll[startOrEnd].classList.remove('active')
    }
    if (obj.checked) {
        if (startOrEnd === 1) {
            query(obj.value)
        } else if (startOrEnd === 2) {
            query('', obj.value)
        }
    }


}

function changePage(pageCount) {
    let str = ``;

    for (let i = 1; i <= pageCount; i++) {
        if (currentPageIndex === i) {
            str += `<a href="javascript:;" class="page-index active" onclick="getPageByIndex(${i})">${i}</a>`

        } else {
            str += `<a href="javascript:;" class="page-index" onclick="getPageByIndex(${i})">${i}</a>`
        }

    }
    document.querySelector('.page').classList.add('active')
    document.querySelector('.page_wrapper').innerHTML = str
    console.log(page_total)
    console.log(currentPageIndex)
}

function getPageByIndex(index) {
    currentPageIndex = index
    query()
}

//查询出发车站
function queryStartStationId(startStationId) {
    query()
}


document.querySelector('.login .close i').addEventListener('click', () => {

    document.querySelector('.shield.active').classList.remove('active')
    document.querySelector('.login.active').classList.remove('active')

})
