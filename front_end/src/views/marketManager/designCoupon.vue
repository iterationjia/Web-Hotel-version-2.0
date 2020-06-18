<template>
    <div class="coupon-wrapper">
        <div style="width: 100%; text-align: right; margin:20px 0">
            <a-button type="primary" @click="addCoupon"><a-icon type="plus" />添加网站优惠</a-button>
        </div>
        <a-table
                :columns="columns"
                :dataSource="couponList"
                bordered
        >
                <span slot="couponType" slot-scope="value">
                <a-tag color="red" v-if="value==4">限时优惠</a-tag>
            </span>
            <span slot="action" slot-scope="record">
                <a-popconfirm
                        title="确定想删除该优惠吗？"
                        @confirm="DeleteCoupon(record)"
                        okText="确定"
                        cancelText="取消"
                >
                            <a-button type="danger" >删除优惠</a-button>
                        </a-popconfirm>
                    </span>
        </a-table>

        <AddCouponModal></AddCouponModal>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddCouponModal from "../marketManager/components/addCoupon";
    const columns = [
        {
            title: '优惠id',
            dataIndex: 'id',
        },
        {
            title: '优惠类型',
            dataIndex: 'couponType',
            scopedSlots: { customRender: 'couponType' }
        },
        {
            title: '优惠名称',
            dataIndex: 'couponName',
        },
        {
            title: '简介',
            dataIndex: 'description',
        },
        {
            title: '达标金额',
            dataIndex: 'targetMoney',
        },
        {
            title: '优惠金额',
            dataIndex: 'discountMoney',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: 'designCoupon',
        data(){
            return {
                columns,
                form: this.$form.createForm(this, { name: 'designCoupon' }),
            }
        },
        components: {
            AddCouponModal,
        },
        computed: {
            ...mapGetters([
                'couponList',
                'couponVisible',
                'addCouponVisible',
            ]),
        },
        mounted() {
            this.getCouponList()
        },
        methods: {
            ...mapMutations([
                'set_couponVisible',
                'set_addCouponVisible',
            ]),
            ...mapActions([
                'getCouponList',
                'deleteCoupon',
            ]),
            addCoupon(){
                this.set_addCouponVisible(true)
            },
            DeleteCoupon(record){
                this.deleteCoupon(record.id)
            }
        }
    }
</script>
