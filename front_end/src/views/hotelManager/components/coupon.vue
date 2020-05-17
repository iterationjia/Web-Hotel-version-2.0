<template>
   <div>
    <a-modal
        :visible="couponVisible"
        title="优惠策略"
        width="900px"
        :footer="null"
        @cancel="cancel"
    >
        <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
        <div style="width: 100%; text-align: right; margin:20px 0">
            <a-button type="primary" @click="addCoupon"><a-icon type="plus"/>添加优惠策略</a-button>
        </div>
        <a-table :columns="columns" :dataSource="couponList">
            <span slot="couponType" slot-scope="value">
                <a-tag color="red" v-if="value==1">生日特惠</a-tag>
                <a-tag color="red" v-if="value==2">多间特惠</a-tag>
                <a-tag color="red" v-if="value==3">满减优惠</a-tag>
                <a-tag color="red" v-if="value==4">限时优惠</a-tag>
            </span>
        </a-table>
    </a-modal>
    <AddCoupon></AddCoupon>
   </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'

const columns = [
    // 这里定义列表头
    {
        title: '优惠类型',
        dataIndex: 'couponType',
        scopedSlots: { customRender: 'couponType' }
    },
    {
        title: '折扣',
        dataIndex: 'discount'
    },
    {
        title: '优惠简介',
        dataIndex: 'description'
    },
    {
        title: '优惠金额',
        dataIndex: 'discountMoney'
    }
  ];
export default {
    name: 'coupon',
    data() {
        return {
            columns
        }
    },
    components: {
        AddCoupon,
    },
    computed: {
        ...mapGetters([
            'couponVisible',
            'couponList',
        ])
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        ...mapActions([
            'getHotelCoupon'
        ]),
        cancel() {
            this.set_couponVisible(false)
        },
        addCoupon() {
            this.set_addCouponVisible(true),
            this.set_couponVisible(false)
        },
    },
}
</script>
<style scoped>

</style>