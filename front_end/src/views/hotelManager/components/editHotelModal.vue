<template>
    <a-modal
            :visible="editHotelModalVisible"
            title="编辑酒店"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
            destroyOnClose
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名">
                <a-input
                        v-decorator="['hotelName', {
                            rules: [{ required: true, message: '请填写酒店名称' }] ,
                            initialValue: info.name
                        }]"
                />
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店地址"
                        v-decorator="['address', {
                            rules: [{ required: true, message: '请填写酒店地址' }] ,
                            initialValue: info.address
                        }]"
                />
            </a-form-item>
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'hotelStar',
                    {
                        rules: [{ required: true, message: '请选择酒店星级' }] ,
                        initialValue: info.hotelStar
                    }]"
                >
                    <a-select-option value="Three">三星级</a-select-option>
                    <a-select-option value="Four">四星级</a-select-option>
                    <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写手机号"
                        v-decorator="['phoneNumber', {
                            rules: [{ required: true, message: '请输入手机号' }],
                            initialValue: info.phoneNum
                        }]"
                />
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写酒店简介"
                        v-decorator="['description', {
                            rules: [{ required: true, message: '请填写酒店简介' }] ,
                            initialValue: info.description
                        }]"
                />
            </a-form-item>
            <a-form-item label="酒店图片" v-bind="formItemLayout">
                <p>需手动上传，不上传则不更改</p>
                <div>
                    <a-upload :defalut-file-list="fileList" list-type="picture" :remove="handleRemove" :before-upload="beforeUpload">
                        <a-button v-if="fileList.length < 1"> <a-icon type="upload" /> Select File </a-button>
                    </a-upload>
                    <a-button
                            type="primary"
                            :disabled="fileList.length === 0"
                            :loading="uploading"
                            style="margin-top: 16px"
                            @click="handleUpload"
                    >
                        {{ uploading ? '上传中' : '上传' }}
                    </a-button>
                </div>
            </a-form-item>
        </a-form>

    </a-modal>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    export default {
        name: "editHotelModal",
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
                hotelInfo:{},
                fileList:[],
                uploading:false
            }
        },
        props: ['info'],
        computed: {
            ...mapGetters([
                'editHotelModalVisible'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'editHotelModal' });
        },
        methods: {
            ...mapMutations([
                'set_editHotelModalVisible',
                'set_addHotelParams'
            ]),
            ...mapActions([
                'editHotel',
                'updateHotelImg'
            ]),
            handleRemove(file) {
                const index = this.fileList.indexOf(file);
                const newFileList = this.fileList.slice();
                newFileList.splice(index, 1);
                this.fileList = newFileList;
            },
            beforeUpload(file) {
                const isLt1M = file.size / 1024 / 1024 < 1;
                if (!isLt1M) {
                    this.$message.error('图片必须小于 1MB!');
                    return true;
                } else {
                    this.fileList = [...this.fileList, file];
                    return false;
                }
            },
            handleUpload(){
                this.updateHotelImg({
                    id: this.info.id,
                    img: this.fileList[0]
                })
            },
            cancel(){
               this.set_editHotelModalVisible(false)
            },
            handleSubmit(e){
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('hotelName'),
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                            phoneNum: this.form.getFieldValue('phoneNumber'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            id: this.info.id
                        }
                        this.editHotel(data)
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>