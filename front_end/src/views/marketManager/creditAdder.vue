<template>
    <div>
        <a-layout>
            <a-layout-content>
                <a-form :form="form" v-bind="formItemLayout" @submit="handleSubmit" style="margin-top: 50px">
                    <a-form-item label="邮箱">
                        <a-input v-model="userEmail">
                            <a-icon slot="prefix" type="edit" />
                            <a-tooltip slot="suffix" title="请输入注册邮箱">
                                <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                            </a-tooltip>
                        </a-input>
                    </a-form-item>
                    <a-form-item label="用户名">
                        <a-input v-decorator="['userName']" @change="setCurcredit">
                            <a-icon slot="prefix" type="user" />
                            <a-tooltip slot="suffix" title="请输入真实姓名">
                                <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                            </a-tooltip>
                        </a-input>
                    </a-form-item>
                    <a-form-item label="充值金额">
                        <a-input prefix="￥" suffix="RMB" v-model="yuans" @change="calCredit"
                        />
                    </a-form-item>
                    <a-form-item label="当前信用点数">
                        <a-input v-model="userCredit" suffix="点" disabled>
                            <a-icon slot="prefix" type="frown" />
                        </a-input>
                    </a-form-item>
                    <a-form-item label="充值信用点数">
                        <a-input v-model="this.paycredit" suffix="点" disabled>
                            <a-icon slot="prefix" type="rise" />
                        </a-input>
                    </a-form-item>
                    <a-form-item label="最终信用点数">
                        <a-input v-model="this.aftercredit" suffix="点" disabled>
                            <a-icon slot="prefix" type="smile" />
                        </a-input>
                    </a-form-item>
                    <a-form-item :label-col="formTailLayout.labelCol" :wrapper-col="formTailLayout.wrapperCol">
                        <a-button type="primary" style="width: 100%" html-type="submit">充值</a-button>
                    </a-form-item>
                </a-form>
            </a-layout-content>
        </a-layout>
    </div>
</template>

<script>
import {mapActions, mapGetters, mapMutations} from "vuex";

const formTailLayout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 8, offset: 8 },
};
export default {
    name: "creditAdder.vue",
    data() {
        return {
            userEmail: null,
            yuans: 0,
            paycredit:0,
            aftercredit:0,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 8 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 8 },
                },
            },
            formTailLayout,
        }
    },
    beforeCreate() {
        this.form=this.$form.createForm(this, {name:'CreditAdder'})
    },
    computed: {
        ...mapGetters([
            'userCredit',
        ]),

    },

    methods: {
        ...mapMutations([
            'set_userCredit',
        ]),
        ...mapActions([
            'creditSet',
            'getCredit',
        ]),
        handleSubmit(e){
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        email: this.userEmail,
                        credit: this.aftercredit,
                    }
                    this.creditSet(data);
                }
                else{
                    console.log("problem occur")
                }
            });
        },
        setCurcredit(){
            const data = {
                email: this.userEmail
            }
            this.getCredit(data)
        },
        calCredit(){
            this.paycredit = this.yuans*100
            this.aftercredit = this.userCredit+this.paycredit
        }
    }
}
</script>

<style scoped>

</style>