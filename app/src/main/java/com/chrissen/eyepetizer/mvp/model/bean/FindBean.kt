package com.chrissen.eyepetizer.mvp.model.bean

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/12.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

data class FindBean(var id : Int, var name : String?, var alis: Any?,
                    var description: String?, var bgPicture: String?,
                    var bgColor: String?, var headerImage: String?){
    /**
     * id : 36
     * name : 生活
     * alias : null
     * description : 匠心、健康、生活感悟
     * bgPicture : http://img.kaiyanapp.com/924ebc6780d59925c8a346a5dafc90bb.jpeg
     * bgColor :
     * headerImage : http://img.kaiyanapp.com/a1a1bf7ed3ac906ee4e8925218dd9fbe.png
     */
}