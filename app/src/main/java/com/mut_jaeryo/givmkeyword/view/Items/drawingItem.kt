package com.mut_jaeryo.givmkeyword.view.Items

import android.os.Parcel
import android.os.Parcelable


/*사용자가 올린 이미지 정보를 담을 클래스
  Declaration은 주제와 부적절한 그림에 대한 신고

 */

data class drawingItem(var id: String?, var keyword: String?, var name: String?, var content:String?, var heart:Int, var isHeart:Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(keyword)
        parcel.writeString(name)
        parcel.writeString(content)
        parcel.writeInt(heart)
        parcel.writeByte(if (isHeart) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<drawingItem> {
        override fun createFromParcel(parcel: Parcel): drawingItem {
            return drawingItem(parcel)
        }

        override fun newArray(size: Int): Array<drawingItem?> {
            return arrayOfNulls(size)
        }
    }

}