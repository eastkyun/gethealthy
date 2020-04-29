function changeHeart(state){
    if(state.classList.contains("fa-heart-o")){
        state.classList.remove("fa-heart-o");
        state.classList.add("fa-heart");
        //좋아요 DB에 추가
    }else{
        state.classList.remove("fa-heart");
        state.classList.add("fa-heart-o");
        //좋아요 DB에서 제거
    }
}