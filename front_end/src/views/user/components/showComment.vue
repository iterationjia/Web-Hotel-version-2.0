<template>
    <div>
        <a-modal
                title="已评价"
                :visible="showCommentVisible"
                :footer="null"
                @cancel="cancel"
        >
            <div>
                <p>您的评分</p>
                <a-rate v-model="info.star" :tooltips="desc" disabled/>
                <span class="ant-rate-text">{{ desc[info.star - 1] }}</span>
                <br/>
                <br/>
                <p>您的评论</p>
                <a-textarea placeholder="请输入您的评价" auto-size v-model="info.comment" disabled/>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import { mapGetters,mapMutations } from 'vuex'
    export default {
        name: "showComment",
        data(){
            return {
                desc: ['terrible', 'bad', 'normal', 'good', 'wonderful'],
            }
        },
        props: ['info'],
        computed: {
            ...mapGetters([
                'showCommentVisible',
            ])
        },
        methods: {
            ...mapMutations([
                'set_showCommentVisible'
            ]),
            cancel(){
                this.set_showCommentVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>