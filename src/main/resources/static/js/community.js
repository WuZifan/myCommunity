

function collapseComment(e){
    console.log(e)
    var id = e.getAttribute("data-id");
    var comment = $("#comment-"+id);
    //没展开的时候怎么操作
    if(!comment.hasClass("in")){
        $.getJSON( "/comment/"+id, function( data ) {
            console.log(data);
            var subCommentContainer = $("#comment-"+id);

            if(subCommentContainer.children().length == 1){
                // var items = []
                $.each( data.data.reverse(), function( index,comment ) {

                    var createDate = new Date(comment.gmtCreate);
                    // var createFormatStr = createDate.Format("yyyy-MM-dd");
                    var year = createDate.getFullYear();
                    var month = createDate.getMonth()+1;
                    var date = createDate.getDate();
                    console.log("year: +"+year)
                    console.log("month: +"+ month)
                    console.log("date: +"+ date)



                    var mediaBodyElement = $("<div/>",{
                       "class":"media-body"
                    }).append($("<h5/",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "class":"commentContent",
                        "html":comment.content
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":year+"-"+month+"-"+date
                    }));

                    var avatarElement = $("<img/>",{
                       "class":"media-object avatar img-rounded",
                        "src":comment.user.avatarUrl
                    });

                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    })

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    });

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 incomments",
                        // "id":"comment-"+id,
                        // html:comment.content
                    });

                    mediaLeftElement.append(avatarElement);
                    mediaElement.append(mediaLeftElement);
                    mediaElement.append(mediaBodyElement);
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });

            }


        });
    }

    comment.toggleClass("in");

}

function postComment() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId,1,commentContent);
}

function  getComment(e) {
    var id = e.getAttribute("data-id");
    var content = $("#input-"+id).val();
    comment2target(id,2,content);
}

function comment2target(targetId,type,content) {
    if(!content){
        alert("不能回复空");
        return ;
    }

    var data = {
        "parentId":targetId,
        "content":content,
        "type":type
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify(data),
        success: function (response) {
            if(response.code == 200){
                location.reload();
                $("#comment_section").hide();
            }else{
                if(response.code==2003){
                    var isAccept = confirm(response.message);
                    if(isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=8a342c05e1bcda89dda4&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closeable",true);
                    }
                }
                else{
                    alert(response.message);
                }

            }
            console.log(response);
        },
        dataType: "json",
        contentType:"application/json"
    });
}

