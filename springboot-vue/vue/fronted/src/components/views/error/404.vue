<template>
	<b-container>
		<b-card elevation="10" outlined width="100%" class="mx-auto">
			<b-card-title>
				<b-icon large color="black">
					mdi-cancel
				</b-icon>
				<span style="width:5px;"></span>
				<span class="mr-2">404 Error</span>
			</b-card-title>
			<b-card-text>
				<span class="mr-2">Page Not Found</span>
			</b-card-text>
				<b-btn @click="prev" color="blue-grey" text>
					<b-icon>
						mdi-arrow-left
					</b-icon>
					<span class="mr-2">Back</span>
				</b-btn>
		</b-card>
	</b-container>
</template>

<script>
export default {
	name: 'PageNotFound',
	methods: {
		prev() {
			this.prevPage()
		},
	},
}
</script>

<style></style>
