<template>
    <a-modal
:visible="addCouponVisible"
title="添加优惠策略"
cancelText="取消"
okText="确定"
@cancel="cancel"
@ok="handleSubmit"
    >
    <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
    <a-form-item label="优惠劵类型">
    <a-select
v-decorator="['couponType', {rules: [{required: true, message: '请选择优惠劵类型'}]}]"
@change="changeType"
    >
    <a-select-option value="1">会员特惠</a-select-option>
    <a-select-option value="2">多间特惠</a-select-option>
    <a-select-option value="3">满减特惠</a-select-option>
    <a-select-option value="4">限时特惠</a-select-option>
    </a-select>
    </a-form-item>
    <a-form-item label="劵名">
    <a-input
placeholder="请填写劵名"
v-decorator="['couponName', { rules: [{ required: true, message: '请填写劵名' }] }]"
    />
    </a-form-item>
    <a-form-item label="优惠简介">
    <a-input
type="textarea"
:rows="4"
placeholder="请填写优惠简介"
v-decorator="['description', { rules: [{ required: true, message: '请填写优惠简介' }] }]"
    />
    </a-form-item>
    <a-form-item label="达标金额">
    <a-input
placeholder="请填写达标金额"
v-decorator="['targetMoney', { rules: [{ required: true, message: '请填写达标金额' }] }]"
    />
    </a-form-item>
    <a-form-item label="优惠金额">
    <a-input
placeholder="请填写优惠金额"
v-decorator="['discountMoney', { rules: [{ required: true, message: '请填写优惠金额' }] }]"
    />
    </a-form-item>
    </a-form>
    </a-modal>
    </template>
    <script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addCouponModal',
    data() {
        return {
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    computed: {
        ...mapGetters([
            'activeHotelId',
            'addCouponVisible',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible'
        ]),
        ...mapActions([
            'addHotelCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
        },
        changeType(v){
            if( v == '3') {

            }else{

            }
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        type: this.form.getFieldValue('couponType'),
                        name: this.form.getFieldValue('couponName'),
                        description: this.form.getFieldValue('description'),
                        targetMoney: this.form.getFieldValue('targetMoney'),
                        discountMoney: this.form.getFieldValue('discountMoney'),
                        hotelId: this.activeHotelId
                    }
                    this.addHotelCoupon(data)
                }
            });
        },
    }
}
</script>