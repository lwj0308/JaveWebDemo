for (let i = 9; i >= 1; i--) {
    for (let j = 1; j <= 9; j++) {
        if (j > i) continue;
        document.write(i + " * " + j + " &nbsp;&nbsp;&nbsp; ")
    }
    document.write("<br>")
}
document.write("<br>")

let count = 0;
let arr = [];
for (let i = 0; i < 100; i++) {
    arr[i] = Math.round(Math.random() * 10);
}
arr.forEach(value => {
    if (value === 5) {
        count++;
    }
})
document.write(arr.toString() + "<br>")
document.write("5出现的次数：" + count + "<br><br>")

let arr2 = [];
for (let i = 0; i < 100; i++) {
    arr2[i] = Math.round(Math.random() * 100);
}
document.write(arr2.toString() + "<br>")
document.write("7的倍数：")
arr2.forEach(value => {
    if (value !== 0 && value % 7 === 0) {
        document.write(value + "&nbsp;")
    }
})
document.write("<br><br>")
let arr3 = [];
for (let i = 0; i < 100; i++) {
    arr3[i] = Math.round(Math.random() * 100);
}
document.write(arr3.toString() + "<br>")
document.write("同时整除3 和5的数字：")
arr3.forEach(value => {
    if (value %3===0 && value % 5 === 0) {
        document.write(value + "&nbsp;")
    }
})

document.write("<br><br>")
// const arr4 = Array.from(Array(8), () => new Array(8));
const arr4 = Array(8)
    .fill([])
    .map(() => Array(8).fill(0));
console.log(arr4);
// for (let i = 0; i < arr4.length; i++) {
//     arr4[i].fill(0)
// }

for (let i = 0; i < 16;) {
    let r = Math.floor(Math.random() * 8);
    let c = Math.floor(Math.random() * 8);
    if (arr4[r][c] === 0){
        arr4[r][c] = 'x';
        i++
    }
}

for (let i = 0; i < arr4.length; i++) {
    document.write(arr4[i]+"<br>")
}


