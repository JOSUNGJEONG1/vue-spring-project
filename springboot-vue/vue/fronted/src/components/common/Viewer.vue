<template>
	<div>
		<Viewer ref="toastViewer" height="500px" />
	</div>
</template>


<script>
import Viewer from '@toast-ui/editor'
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
export default {
	components: {
		Viewer,
	},
	methods: {
		setContent(content) {
			this.$refs.toastViewer.invoke('setMarkdown', content)
		},
	},
}
</script>
