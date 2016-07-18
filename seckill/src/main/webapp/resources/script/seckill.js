/**
 * Created by lifuz on 2016/7/18.
 */

var seckill = {
    //封装秒杀相关ajax的url
    URL : {

    },

    validatePhone : function (phone) {
        if (phone && phone == 11 && isNaN(phone)){
            return true;
        } else {
            return false;
        }
    },

    //详情页秒杀逻辑
    detail: {

        init : function (params) {
            //手机验证和登录，计时交互

            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var entTime = params['endTime'];
            var seckillId = params['seckillId'];

            if (!seckill.validatePhone(killPhone)){
                //绑定phone
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                   show:true,
                    backdrop: 'static',
                    keyboard:false
                });

                $('#killPhoneBtn').click(function () {
                   var inputPhone = $('killPhoneKey').val();
                    if( seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone',inputPhone,{expirs:7,path:'/seckill'});
                        window.location.reload();
                    } else {

                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误！</label>').show(300);

                    }
                });
            }

        }

    }
}