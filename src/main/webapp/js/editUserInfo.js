// 恢复值的函数
function restoreValue(inputElement, originalValue) {
    if (inputElement.value.trim() === '') {
        // 如果内容为空，则恢复为原始值
        inputElement.value = originalValue;
    }
}

function handleFileSelect() {
    var fileInput = document.getElementById('headSculpture');
    var previewImg = document.getElementById('preview');

    var file = fileInput.files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            previewImg.src = e.target.result;
        };
        reader.readAsDataURL(file);
    } else {
        previewImg.src = '#';
    }
}