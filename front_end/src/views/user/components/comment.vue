<template>
    <div>
        <a-modal
                title="评价"
                :visible="commentVisible"
                @ok="handleOk()"
                @cancel="handleCancel"
                okText="确定"
                cancelText="取消"
        >
            <div>
                <p>评分</p>
                <a-rate v-model="stars" :tooltips="desc" />
                <br/>
                <br/>
                <p>评论</p>
                <a-textarea placeholder="请输入您的评价" auto-size v-model="comments"/>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from 'vuex'
    export default {
        name: "comment",
        inject:['reload'],
        data(){
            return {
                stars: 3,
                desc: ['terrible', 'bad', 'normal', 'good', 'wonderful'],
                comments: null,
            }
        },
        props: ['recordId'],
        computed: {
            ...mapGetters([
                'commentVisible',
            ])
        },
        methods: {
            ...mapMutations([
                'set_commentVisible'
            ]),
            ...mapActions([
                'updateUserOrderComment',
            ]),
            handleOk() {
                const data = {
                    star: this.stars,
                    comment: this.comments,
                    id: this.recordId,
                }
                this.updateUserOrderComment(data);
                this.set_commentVisible(false);
                this.stars = 0;
                this.comments = null;
                this.reload();
            },
            handleCancel(e) {
                this.set_commentVisible(false);
                this.stars = 0;
                this.comments = null;
            },
        }
    }
</script>

<style scoped>

</style>