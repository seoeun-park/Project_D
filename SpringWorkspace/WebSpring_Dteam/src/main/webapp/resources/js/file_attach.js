/**
 * 파일첨부 관련 처리
 */
 $('#attach-file').on('change', function(){
 	if( this.files[0] )	$('#file-name').text(this.files[0].name );
 	$('#delete-file').css('display', 'inline');
 });
 
 $('#delete-file').on('click', function(){
 	$('#file-name').text('');
 	$('#attach-file').val('');
 	$('#delete-file').css('display', 'none');
 });