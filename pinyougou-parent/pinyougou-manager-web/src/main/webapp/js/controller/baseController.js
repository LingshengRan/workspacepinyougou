//基本控制层
app.controller('baseController',function($scope){

    //重新加载列表 数据
    $scope.reloadList = function () {
        //切换页码
        // $scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);

        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }

    //分页控件配置
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    }

    $scope.selectIds = [];//选中ID集合
    //用户勾选复选框
    $scope.updateSelection = function ($event, id) {
        //$event源，表示当前的整个标签！
        if ($event.target.checked) {//如果是被选中,则增加到数组
            $scope.selectIds.push(id);//向集合中添加元素
        } else {
            var idx = $scope.selectIds.indexOf(id);//查找需要移除的位置
            $scope.selectIds.splice(idx, 1);//参数1：移除的位置  参数2：移除的个数
        }
    }

});