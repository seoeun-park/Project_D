/**
 * 
 */
 //이미지여부 판단
function isImage(filename){
	//abc.txt, abc.png, abc.jpg....
	var ext = filename.substring( filename.lastIndexOf('.')+1 ).toLowerCase();
	var imgs = [ 'jpg', 'jpeg', 'gif', 'png', 'bmp' ];
	if(imgs.indexOf(ext) > -1 ) return true;
	else return false;
}

$('#attach-file').on('change', function(){
	//파일정보의 파일명이 이미지파일인 경우 미리보기
	var attach = this.files[0];
	if( attach ){
		if(  isImage( attach.name ) ){
			var img = '<img style="border-radius:20%; width:100px; height:100px;" class="file-img" id="preview-img" src="" />';
				$('#preview').html(img);

				var reader = new FileReader();
				reader.onload = function(e){
					$('#preview-img').attr('src', e.target.result);
					
				}
				reader.readAsDataURL(attach);
			}else $('#preview').html('');
		}

	
});

$('#delete-file').on('click', function(){
	$('#preview').html('');
	
});
 