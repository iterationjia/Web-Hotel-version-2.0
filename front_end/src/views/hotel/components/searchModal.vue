<template>
    <a-modal
            :visible="searchModalVisible"
            title="酒店搜索"
            cancelText="取消"
            okText="搜索"
            @cancel="cancel"
            @ok="handleSubmit"
            destroyOnClose
    >
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
        </a-form>
    </a-modal>
</template>

<script>
    import { mapActions, mapMutations, mapGetters } from "vuex";

    export default {
        name: "searchModal",
        computed: {
            ...mapGetters([
                'searchModalVisible'
            ])
        },
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
                marks: {0:'0', 1:'1', 2:'2', 3:'3', 4:'4', 5:'5',},
            }
        },
        beforeCreate() {
            this.form=this.$form.createForm(this, {name:'hotelSearch'})
        },
        methods: {
            ...mapMutations([
               'set_searchModalVisible'
            ]),
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
                        this.getHotelListBySearch(data)
                        // this.$router.push({ name: 'hotelList'})
                        // this.addHotelCoupon(data)
                    }
                    else{
                        console.log("wtf")
                    }
                });
            },
            cancel() {
                this.set_searchModalVisible(false);
            }
        }
    }
</script>

<style scoped>

</style>