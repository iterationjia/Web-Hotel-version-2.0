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
                        <a-input v-decorator="['userName']" @change="setCurlv">
                            <a-icon slot="prefix" type="user" />
                            <a-tooltip slot="suffix" title="请输入真实姓名">
                                <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                            </a-tooltip>
                        </a-input>
                    </a-form-item>
                    <a-form-item label="充值金额">
                        <a-input prefix="￥" suffix="RMB" v-model="yuans" @change="callv"
                        />
                    </a-form-item>
                    <a-form-item label="充值前等级">
                        <a-input v-model="userlv" suffix="级" disabled>
                            <a-icon slot="prefix" type="frown" />
                        </a-input>
                    </a-form-item>
                    <a-form-item label="充值后等级">
                        <a-input v-model="this.afterlv" suffix="级" disabled>
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
        name: "lvAdder.vue",
        data() {
            return {
                userEmail: null,
                yuans: 0,
                afterlv:0,
                aftertotalmoney: 0,
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
            this.form=this.$form.createForm(this, {name:'lvAdder'})
        },
        computed: {
            ...mapGetters([
                'userlv',
                'totalmoney',
            ]),

        },

        methods: {
            ...mapMutations([
                'set_userlv',
                'set_userTotalMoney',
            ]),
            ...mapActions([
                'getTotalMoney',
                'getlv',
                'lvSet',
            ]),
            handleSubmit(e){
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            email: this.userEmail,
                            totalmoney: this.aftertotalmoney,
                        }
                        this.lvSet(data);
                    }
                    else{
                        console.log("problem occur")
                    }
                });
            },
            setCurlv(){
                const data = {
                    email: this.userEmail
                }
                this.getlv(data)
                this.getTotalMoney(data)
            },
            callv(){
                this.aftertotalmoney = Number(this.totalmoney)+Number(this.yuans);
                this.afterlv =  Math.floor((this.aftertotalmoney<=10000)?(this.aftertotalmoney/1000):(9+this.aftertotalmoney/10000))
            }
        }
    }
</script>

<style scoped>

</style>