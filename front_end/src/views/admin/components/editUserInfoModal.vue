<template>
    <a-modal
            :visible="editUserInfoModalVisible"
            title="更改信息"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
            destroyOnClose
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input

                        v-decorator="[
                        'userName',
                       { rules: [{required:true}],
                        initialValue:info.userName,
                       }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input

                        v-decorator="[
                        'password',
                         { rules: [{required:true}],
                            initialValue:info.password,
                         }
                    ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                        v-decorator="[
                        'phoneNumber',
                         { rules: [{required:true}],
                            initialValue:info.phoneNumber,
                         }
                    ]"
                />
            </a-form-item>
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'editUserInfoModal',
        props:['info'],
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
                userInfo:{},
            }
        },
        computed: {
            ...mapGetters([
                'editUserInfoModalVisible',
                'userList',
                'userid',
                'editUserInfoParams',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'editUserInfoModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_editUserInfoModalVisible',
                'set_editUserInfoParams',

            ]),
            ...mapActions([
                'getUserList',
                'editUserInfo',
            ]),
            cancel() {
                //console.log(this.userid)
                this.set_editUserInfoModalVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();

                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        //console.log(this.editUserInfoParams)
                        const data = {
                            userName:this.form.getFieldValue('userName'),
                            password: this.form.getFieldValue('password'),
                            phoneNumber:this.form.getFieldValue('phoneNumber'),

                        }
                        //console.log(data)
                        this.set_editUserInfoParams(data)
                        //console.log(this.editUserInfoParams)
                        this.editUserInfo()
                    }
                });
            },
        }
    }
</script>