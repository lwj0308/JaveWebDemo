let error = document.querySelectorAll(".error-tips");
let inputElements = document.querySelectorAll("input");
let dataNums = document.querySelectorAll("[data-num]");


for (let i = 0; i < dataNums.length; i++) {
    dataNums[i].onblur=()=>{
        switch (i) {
            case 0:
                if (dataNums[i].value.length < 6) {
                    error[i].style.display = "flex"
                    error[i].children[1].innerHTML = "<i class='icon icon-error'></i>用户名长度不能少于6位！";
                } else if (!/^[a-zA-Z][a-zA-Z0-9_]*$/.test(dataNums[i].value)) {
                    error[i].style.display = "flex"
                    error[i].children[1].innerHTML = "<i class='icon icon-error'></i>用户名只能由字母、数字和_组成,须以字母开头！";
                } else {
                    error[i].style.display = "none"
                }
                break
            case 1:
                if (dataNums[i].value.length < 6) {
                    error[i].style.display = "flex"
                    error[i].children[1].innerHTML = "<i class='icon icon-error'></i>密码长度不能少于6位！";
                } else if (!/\d+\w+\S+/.test(dataNums[i].value)) {
                    error[i].style.display = "flex"
                    error[i].children[1].innerHTML = "<i class='icon icon-error'></i>密码必须是由字母、数字和符号组成！";
                }else {
                    error[i].style.display = "none"
                }
                break
            case 2:
                if (dataNums[i].value !==dataNums[i-1].value) {
                    error[i].style.display = "flex"
                    error[i].children[1].innerHTML = "<i class='icon icon-error'></i>两次密码不一致！";
                } else {
                    error[i].style.display = "none"
                }
                break
            case 4:
                if (dataNums[i].value.length < 18) {
                    error[i].style.display = "flex"
                    error[i].children[1].innerHTML = "<i class='icon icon-error'></i>格式不对！";
                } else {
                    error[i].style.display = "none"
                }
                break
        }
    }
}
document.querySelector('#next-step').addEventListener('click',(ev)=>{
 
    ev.preventDefault()
    for (let i = 0; i < dataNums.length; i++) {
        if (!dataNums[i].value) {
            error[i].style.display = "flex"
            error[i].children[1].innerHTML = "<i class='icon icon-error'></i>请输入内容！";
        } else {
            error[i].style.display = "none"
        }
    }
    
})


