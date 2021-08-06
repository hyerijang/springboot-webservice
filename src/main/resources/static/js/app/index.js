// function 안에서 변수 접근 권한 제한
// 브라우저의 스코프는 공용 공간으로 쓰이기 때문에 나중에 로딩된 a.js의 init,save 가 먼저 로딩된 js의 function 덮어쓰게된다.
// 때문에 var main 객체를 만들어서
// 해당 객체에서 필요한 모든 function 을 선언, main 객체 내에서만 function 이 유효하도록 한다.
var main = {
    init: function () {
        //https://eotkd4791.github.io/javascript/JavaScript15/
        //UnderScore(-)
        // 변수 명 앞에 _를 명시하는 변수 작성법은 접근 제어자 private 의 특성이 적용되어야 하는 변수나 함수 앞에, 그것을 명시하고자 쓰여왔다.
        //일종의 관례로 꼭 그렇게 할 필요는 없지만 많은 개발자들이 이와같이 쓴다고 함.

        //Dollar Sign($)
        //자바스크립트에서 $는 getElementById(id)의 줄임말처럼 사용됨.
        //getElementById(id)를 반복하면 코드가 길고 복잡해지기 때문
        //어디까지나 관례이므로 사용하든 말든 상관은 없음.
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        //id : btn-update 라는 HTML 엘리먼트가
        //클릭되었을때 실행되도록 이벤트를 등록
        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    //신규로 추가 될 update function (p.154)
    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        //Ajax는 빠르게 동작하는 동적인 웹 페이지를 만들기 위한 개발 기법
        //Ajax는 html페이지 전체가 아니라 필요한 부분만을 갱신할 수 있도록
        //XMLHttpRequset 객체를 통해서 요청
        //jquery 사용할 경우 아래와 같이 간편하게 쓸수 있다.
        $.ajax({
            type: 'PUT', //PostsApiController에서 이미 put으로 선언되었으므로 여기도 put, REST에서 CRUD는 POST,GET,PUT,DELETE로 맵핑된다.
            url: '/api/v1/posts/' + id, //어느 게시글을 수정할 지 url 패스로 구분하기 위해 Path에 id를 추가
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/'; //수정 후 메인 화면으로 이동
        }).fail(function (error) {
            //JSON.stringify(object)
            //JavaScript 값이나 객체를 JSON 문자열로 변환
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();