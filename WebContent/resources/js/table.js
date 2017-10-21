/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 16-5-16
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
function altRows(id){
    if(document.getElementsByTagName){
        var table = document.getElementById(id);
        var rows = table.getElementsByTagName("tr");
        for(i = 0; i < rows.length; i++){
            if(i % 2 == 0){
                rows[i].className = "evenrowcolor";
            }else{
                rows[i].className = "oddrowcolor";
            }
        }
    }
}