package com.mut_jaeryo.givmkeyword.view.Items




/*사용자가 올린 이미지 정보를 담을 클래스
  Declaration은 주제와 부적절한 그림에 대한 신고

 */
data class drawingItem(var id:String,var keyword:String, var name:String,var content:String,var heart:Int,var isHeart:Boolean) {
}