/**
 * 회원가입시 유효성 체크
 */
 var space = /\s/g;
 
 var join = {

 	common: {
 		empty: { code: "invalid", desc: "입력하세요" },
 		space: { code: "invalid", desc: "공백없이 입력하세요"}
 	},
 	
 	userid: {
 		valid: { code: "valid", desc: "아이디 중복확인하세요" },
 		invalid: { code: "invalid", desc: "아이디는 이메일 형식으로 입력하세요" },
 		usable: { code: "valid", desc: "사용가능한 아이디입니다" },
 		unUsable: { code: "invalid", desc: "이미 사용중인 아이디입니다" }
 	},
 
 	userid_status: function( id ) {
 		var reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
 		
 		if( id == "" ) 				return this.common.empty;
 		else if ( id.match(space) ) return this.common.space;
 		else if ( reg.test(id) )	return this.userid.valid;
 		else						return this.userid.invalid;	
 	},
 	
 	userpw: {
 		valid: { code: "valid", desc: "사용가능한 비밀번호입니다." },
 		min: {code: "invalid", desc: "비밀번호를 최소 8자 이상 입력해주세요."},
 		invalid: { code: "invalid", desc: "비밀번호는 영문 소문자, 숫자 특수문자를 입력하세요." },
 		lack: {code: "invalid", desc: "영문 영문 소문자, 숫자 특수문자를 모두 포함해야 합니다."},
 		equal: {code: "valid", desc: "비밀번호가 일치합니다."},
 		notEqual: { code: "invalid", desc: "비밀번호가 일치하지 않습니다." }
 	},
 	
 	userpw_status: function(pw) {
 		var reg = /[^a-z0-9#?!@$%^&*-]/g;
 		var special = /[#?!@$%^&*-]/g, 	lower = /[a-z]/g, 	digit = /[0-9]/g;
 	
 		if( pw == "" )				return this.common.empty;
 		else if ( pw.match(space) ) return this.common.space;
 		else if ( pw.length < 8 ) 	return this.userpw.min;
 		else if ( reg.test(pw) )	return this.userpw.invalid;
 		else if ( !lower.test(pw) || !digit.test(pw) || !special.test(pw) )	
 									return this.userpw.lack;							
 		else						return this.userpw.valid;

 	},
 	
 	userpw_ck_status: function(pw_ck){
   		if(pw_ck == $("[name=member_pw]").val() )	return this.userpw.equal;
   		else                              	return this.userpw.notEqual;
	},
	
	usernickname: {
 		valid: { code: "valid", desc: "닉네임 중복확인 해주세요." },
 		invalid: { code: "invalid", desc: "닉네임은 2~8자 영문 대소문자, 한글만 사용가능 합니다." },
 		usable: { code: "valid", desc: "사용가능한 닉네임입니다" },
 		unUsable: { code: "invalid", desc: "이미 사용중인 닉네임입니다" }
	},
	
	usernickname_status: function(nickname) {
		var reg = /^[ㄱ-ㅎ가-힣a-zA-Z]{2,8}$/;
		
		if( nickname == "" )				return this.common.empty;
 		else if ( nickname.match(space) ) return this.common.space;
 		else if ( !reg.test(nickname) )	return this.usernickname.invalid;
 		else						return this.usernickname.valid;
	},
	
	usertel: {
 		invalid: { code: "invalid", desc: "숫자만 입력하세요(- 제외)." },
 		valid: { code: "valid", desc: "제대로 입력." }
 	},
	
	usertel_status: function(tel) {
		var reg = /^01(?:0|1|[6-9])(\d{3}|\d{4})(\d{4})$/;
		
		if( tel == "" )				return this.common.empty;
 		else if ( tel.match(space) ) return this.common.space;
 		else if ( !reg.test(tel) )	return this.usertel.invalid;
 		else 						return this.usertel.valid;

	},
	
	userid_usable: function( data ) {
		if( data )		return this.userid.usable;
		else			return this.userid.unUsable;
	},
	
	usernickname_usable: function( data ) {
		if( data )		return this.usernickname.usable;
		else			return this.usernickname.unUsable;
	},
 	
 	tag_status: function( tag ) {
 		var data = tag.val();
 		tag = tag.attr("name");
 	
 		if( tag == "member_id") data = this.userid_status( data );
 		else if( tag == "member_pw") data = this.userpw_status( data );
 		else if( tag == "pw_ck" ) data = this.userpw_ck_status( data );
 		else if( tag == "member_nickname" ) data = this.usernickname_status( data );
 		else if( tag == "member_tel" ) data = this.usertel_status( data );
 		return data;
 	}	
 }