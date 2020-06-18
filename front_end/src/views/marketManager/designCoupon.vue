<template>
    <div class="coupon-wrapper">
        <a-tabs>

                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addCoupon"><a-icon type="plus" />添加网站优惠</a-button>
                </div>

        </a-tabs>
        <AddCouponModal></AddCouponModal>
        <Coupon></Coupon>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import Coupon from '../marketManager/components/coupon'
    import AddCouponModal from "../marketManager/components/addCoupon";


    export default {
        components: {
            AddCouponModal,
            Coupon,
        },
        computed: {
            ...mapGetters([
                // 'orderList',
                'hotelList',
                'managerHotelList',
                'managerOrderTypeList',
                'managerScheduledOrderList',
                'managerExecutedOrderList',
                'managerErrorOrderList',
                'addHotelModalVisible',
                'addRoomModalVisible',
                'activeHotelId',
                'couponVisible',
                'addCouponVisible',
            ]),
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addHotelModalVisible',
                'set_manageRoomModalVisible',
                'set_editHotelModalVisible',
                'set_couponVisible',
                'set_activeHotelId',
                'set_orderDetailVisible',
                'set_managerOrderListType',
                'set_managerOrderTypeList',
                'set_addCouponVisible'
            ]),
            ...mapActions([
                'getHotelList',
                'getHotelDetail',
                'deleteHotel',
                'getManagerHotelList',
                'getManagerOrderList',
                // 'getAllOrders',
                'execOrder',
                'setOrderExcep',
                'checkOutOrder',
                'getHotelCoupon',
                'changeHotelTotalMoney',
                'recoverOrder',
            ]),
            addCoupon(){
                this.set_addCouponVisible(true),
                    this.set_couponVisible(false)
            }
        }
    }
</script>
