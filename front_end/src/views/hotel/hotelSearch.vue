<template>
    <div>
        <a-layout>
            <a-layout-content>
                <a-form :form="form" v-bind="formItemLayout" @submit="handleSubmit" style="margin-top: 50px">
                    <a-form-item label="商圈">
                        <a-select
                                v-decorator="['bizRegion', {rules:[{required:true, message:'请选择商圈'}]}]"
                        >
                            <a-select-option value="XiDan">西单</a-select-option>
                            <a-select-option value="DongDan">东单</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="地址">
                        <a-input
                                 v-decorator="['address',]"
                        />
                    </a-form-item>
                    <a-form-item label="酒店名称">
                        <a-input
                                v-decorator="['name',]"
                        />
                    </a-form-item>
                    <a-form-item label="星级">
                        <a-select
                                  v-decorator="['hotelStar',]"
                        >
                            <a-select-option value="Three">三星级</a-select-option>
                            <a-select-option value="Four">四星级</a-select-option>
                            <a-select-option value="Five">五星级</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item label="评分区间">
                        <a-slider range :marks="marks"  :min="0" :max="5"
                                  v-decorator="['rate',{initialValue:[0,5]}]"
                        />
                    </a-form-item>
                    <a-form-item :label-col="formTailLayout.labelCol" :wrapper-col="formTailLayout.wrapperCol">
                        <a-button type="primary" style="width: 100%" html-type="submit">搜索</a-button>
                    </a-form-item>
                </a-form>
            </a-layout-content>
        </a-layout>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    const formTailLayout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 8, offset: 8 },
    };
    export default {
        name: "hotelSearch",
        data() {
            return {
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
                marks: {0:'0', 1:'1', 2:'2', 3:'3', 4:'4', 5:'5',},
                // form: this.$form.createForm(this, {name:'hotelSearch'})
            }
        },
        beforeCreate() {
            this.form=this.$form.createForm(this, {name:'hotelSearch'})
        },
        methods: {
            ...mapActions([
                'getHotelListBySearch'
            ]),
            handleSubmit(e){
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            region: this.form.getFieldValue('bizRegion'),
                            address: this.form.getFieldValue('address'),
                            name: this.form.getFieldValue('name'),
                            star: this.form.getFieldValue('hotelStar'),
                            rate: this.form.getFieldValue('rate'),
                        }
                        if (data.address==undefined){
                            data.address=""
                        }
                        if (data.name==undefined){
                            data.name=""
                        }
                        if (data.star==undefined){
                            data.star=""
                        }
                        // console.log(data);
                        this.getHotelListBySearch(data)
                        this.$router.push({ name: 'hotelList'})
                        // this.addHotelCoupon(data)
                    }
                    else{
                        console.log("wtf")
                    }
                });
            }
        }
    }
</script>

<style scoped>

</style>