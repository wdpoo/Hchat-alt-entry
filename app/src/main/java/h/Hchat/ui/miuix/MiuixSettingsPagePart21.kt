package h.Hchat.ui.miuix

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ClipData
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Handler
import android.os.Bundle
import android.os.Looper
import android.os.Parcelable
import android.os.SystemClock
import android.provider.OpenableColumns
import android.provider.MediaStore
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.widget.DatePicker
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TimePicker
import android.widget.Toast
import android.widget.VideoView
import h.Hchat.BuildConfig
import h.Hchat.R
import h.Hchat.crash.CrashReportRuntime
import h.Hchat.crash.CrashReportSettings
import h.Hchat.crash.CrashReportSettingsProvider
import h.Hchat.hooks.api.ui.HchatAgentIconDrawable
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutFeature
import h.Hchat.hooks.items.script.market.PluginMarketClient
import h.Hchat.hooks.items.script.market.PluginMarketSettings
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyph
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyphDrawable
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutGlyphs
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconPickResult
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconPicker
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutIconStore
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutItem
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutRuntime
import h.Hchat.hooks.items.floatingshortcut.FloatingShortcutSettings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.SideEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import h.Hchat.hooks.api.core.WeChatApis
import h.Hchat.hooks.api.media.WeChatFavoriteItem
import h.Hchat.hooks.api.message.WeChatMessageObserveApi
import h.Hchat.hooks.api.model.ContactLabelBean
import h.Hchat.hooks.api.model.WeChatContact
import h.Hchat.hooks.items.audiotransform.AudioTransformFeature
import h.Hchat.hooks.items.audiotransform.AudioTransformCancelledException
import h.Hchat.hooks.items.audiotransform.AudioTransformSegmenter
import h.Hchat.hooks.items.audiotransform.AudioTransformSettings
import h.Hchat.hooks.items.antirecall.AntiRecallFeature
import h.Hchat.hooks.items.antirecall.AntiRecallSettings
import h.Hchat.hooks.items.autoreply.AutoReplyFeature
import h.Hchat.hooks.items.autoreply.AutoReplyRule
import h.Hchat.hooks.items.autoreply.AutoReplyRuntime
import h.Hchat.hooks.items.autoreply.AutoReplySettings
import h.Hchat.hooks.items.autoreply.AutoReplyStep
import h.Hchat.hooks.items.autoreply.XiaozhiMcpStatus
import h.Hchat.hooks.items.autoreply.AutoReplyXiaozhiConfig
import h.Hchat.hooks.items.autoreply.AutoReplyZhiliaConfig
import h.Hchat.hooks.items.autoreply.XiaozhiAgentOption
import h.Hchat.hooks.items.autoreply.XiaozhiConsoleApi
import h.Hchat.hooks.items.autoreply.XiaozhiModelOption
import h.Hchat.hooks.items.autoreply.XiaozhiVoiceOption
import h.Hchat.hooks.items.autooriginal.AutoOriginalImageFeature
import h.Hchat.hooks.items.autooriginal.AutoOriginalImageSettings
import h.Hchat.hooks.items.autovieworiginal.AutoViewOriginalFeature
import h.Hchat.hooks.items.autovieworiginal.AutoViewOriginalSettings
import h.Hchat.hooks.items.atallnotify.AtAllNotificationBlockFeature
import h.Hchat.hooks.items.atallnotify.AtAllNotificationBlockSettings
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardFeature
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardRule
import h.Hchat.hooks.items.automessageforward.AutoMessageForwardSettings
import h.Hchat.hooks.items.callmedialimit.CallMediaLimitFeature
import h.Hchat.hooks.items.callmedialimit.CallMediaLimitSettings
import h.Hchat.hooks.items.callmedialimit.CallRingtoneBlockFeature
import h.Hchat.hooks.items.chattime.ChatTimeStyleFeature
import h.Hchat.hooks.items.chattime.ChatTimeStyleSettings
import h.Hchat.hooks.items.conversationgroup.ConversationGroup
import h.Hchat.hooks.items.conversationgroup.ConversationGroupFeature
import h.Hchat.hooks.items.conversationgroup.ConversationGroupPickerSupport
import h.Hchat.hooks.items.conversationgroup.ConversationGroupRuntime
import h.Hchat.hooks.items.conversationgroup.ConversationGroupStore
import h.Hchat.hooks.items.conversationtabs.ConversationTabsFeature
import h.Hchat.hooks.items.conversationtabs.ConversationTabsStore
import h.Hchat.hooks.items.custombottombar.CustomBottomBarFeature
import h.Hchat.hooks.items.custombottombar.FloatingBottomBarSettings
import h.Hchat.hooks.items.customnotify.CustomNotificationFeature
import h.Hchat.hooks.items.customnotify.CustomNotificationRule
import h.Hchat.hooks.items.customnotify.CustomNotificationRuntime
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarFeature
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarPicker
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarSettings
import h.Hchat.hooks.items.customfriendavatar.CustomFriendAvatarStore
import h.Hchat.hooks.items.customnotify.CustomNotificationSettings
import h.Hchat.hooks.items.editmsg.EditMessageFeature
import h.Hchat.hooks.items.editmsg.EditMessageSettings
import h.Hchat.hooks.items.emojisave.EmojiSaveFeature
import h.Hchat.hooks.items.emojisave.EmojiSaveSettings
import h.Hchat.hooks.items.fakelocation.FakeLocationFeature
import h.Hchat.hooks.items.fakelocation.FakeLocationSettings
import h.Hchat.hooks.items.fakescancamera.FakeScanCameraFeature
import h.Hchat.hooks.items.fakescancamera.FakeScanCameraSettings
import h.Hchat.hooks.items.fakevoiceduration.FakeVoiceDurationFeature
import h.Hchat.hooks.items.fakevoiceduration.FakeVoiceDurationSettings
import h.Hchat.hooks.items.forwardlimit.RemoveForwardLimitFeature
import h.Hchat.hooks.items.forwardlimit.RemoveForwardLimitSettings
import h.Hchat.hooks.items.gameemoji.GameEmojiFeature
import h.Hchat.hooks.items.gameemoji.GameEmojiSettings
import h.Hchat.hooks.items.groupleave.GroupLeaveMonitorFeature
import h.Hchat.hooks.items.groupleave.GroupLeaveReplyTemplate
import h.Hchat.hooks.items.groupleave.GroupLeaveReplyTemplateBinding
import h.Hchat.hooks.items.groupleave.GroupLeaveMonitorSettings
import h.Hchat.hooks.items.grouplabel.GroupChatLabel
import h.Hchat.hooks.items.grouplabel.GroupChatLabelFeature
import h.Hchat.hooks.items.grouplabel.GroupChatLabelStore
import h.Hchat.hooks.items.grouplabel.QuickGroupChatLabelFeature
import h.Hchat.hooks.items.grouplabel.QuickGroupChatLabelSettings
import h.Hchat.hooks.items.groupnicknamecolor.GroupNicknameColorSettings
import h.Hchat.hooks.items.groupnicknamecolor.GroupNicknameColorStore
import h.Hchat.hooks.items.grouprename.GroupRenameMonitorFeature
import h.Hchat.hooks.items.grouprename.GroupRenameReplyTemplate
import h.Hchat.hooks.items.grouprename.GroupRenameMonitorSettings
import h.Hchat.hooks.items.grouprename.GroupRenameTemplateBinding
import h.Hchat.hooks.items.hideavatar.HideChatAvatarFeature
import h.Hchat.hooks.items.hideavatar.HideChatAvatarSettings
import h.Hchat.hooks.items.hidemenu.HideChatMenuFeature
import h.Hchat.hooks.items.hidemenu.HideChatMenuSettings
import h.Hchat.hooks.items.hotupdate.DisableHotUpdateFeature
import h.Hchat.hooks.items.hotupdate.DisableHotUpdateSettings
import h.Hchat.hooks.items.hometextcolor.HomeTextColorFeature
import h.Hchat.hooks.items.hometextcolor.HomeTextColorSettings
import h.Hchat.hooks.items.inputhint.InputHintFeature
import h.Hchat.hooks.items.inputhint.InputHintSettings
import h.Hchat.hooks.items.inputhint.InputHintStats
import h.Hchat.hooks.items.inputhint.OutgoingMessageStatsRepository
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveFeature
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveRuntime
import h.Hchat.hooks.items.keepalive.WeChatKeepAliveSettings
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationFeature
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationRuntime
import h.Hchat.hooks.items.keywordnotify.KeywordNotificationSettings
import h.Hchat.hooks.items.keywordnotify.KeywordRule
import h.Hchat.hooks.items.zombiecheck.ZombieCheckController
import h.Hchat.hooks.items.zombiecheck.ZombieCheckActionResult
import h.Hchat.hooks.items.zombiecheck.ZombieCheckFeature
import h.Hchat.hooks.items.zombiecheck.ZombieCheckResultType
import h.Hchat.hooks.items.zombiecheck.ZombieCheckSettings
import h.Hchat.hooks.items.membertitle.MemberTitleFeature
import h.Hchat.hooks.items.membertitle.MemberTitleSettings
import h.Hchat.hooks.items.membertitle.MemberTitleStore
import h.Hchat.hooks.items.messageaffix.MessageAffixFeature
import h.Hchat.hooks.items.messageaffix.MessageAffixSettings
import h.Hchat.hooks.items.messagebubble.MessageBubbleFeature
import h.Hchat.hooks.items.messagebubble.MessageBubblePickResult
import h.Hchat.hooks.items.messagebubble.MessageBubblePicker
import h.Hchat.hooks.items.messagebubble.MessageBubbleSettings
import h.Hchat.hooks.items.messagebubble.MessageBubbleSlot
import h.Hchat.hooks.items.messagebubble.MessageBubbleStore
import h.Hchat.hooks.items.messagebubble.MessageBubbleKind
import h.Hchat.hooks.items.messagetextcolor.MessageTextColorFeature
import h.Hchat.hooks.items.messagetextcolor.MessageTextColorSettings
import h.Hchat.hooks.items.messageblock.MessageBlockBinding
import h.Hchat.hooks.items.messageblock.MessageBlockDefaultRule
import h.Hchat.hooks.items.messageblock.MessageBlockFeature
import h.Hchat.hooks.items.messageblock.MessageBlockSettings
import h.Hchat.hooks.items.messageblock.MessageBlockTemplate
import h.Hchat.hooks.items.messageforward.MessageForwardFeature
import h.Hchat.hooks.items.messageforward.MessageForwardSettings
import h.Hchat.hooks.items.miniprogrambaselib.FakeMiniProgramBaseLibFeature
import h.Hchat.hooks.items.miniprogrambaselib.FakeMiniProgramBaseLibSettings
import h.Hchat.hooks.items.miniprogramsplashad.SkipGlobalMiniProgramSplashAdsFeature
import h.Hchat.hooks.items.miniprogramsplashad.SkipGlobalMiniProgramSplashAdsSettings
import h.Hchat.hooks.items.miniprogramvideoad.SkipMiniProgramVideoAdsFeature
import h.Hchat.hooks.items.miniprogramvideoad.SkipMiniProgramVideoAdsSettings
import h.Hchat.hooks.items.musicorder.QQMusicOrderFeature
import h.Hchat.hooks.items.musicorder.QQMusicOrderSettings
import h.Hchat.hooks.items.moments.OriginalMomentsUploadFeature
import h.Hchat.hooks.items.moments.OriginalMomentsUploadSettings
import h.Hchat.hooks.items.moments.MomentsAutoCommentFeature
import h.Hchat.hooks.items.moments.MomentsAutoCommentSettings
import h.Hchat.hooks.items.moments.MomentsAutoLikeFeature
import h.Hchat.hooks.items.moments.MomentsAutoLikeSettings
import h.Hchat.hooks.items.moments.MomentsAutoForwardFeature
import h.Hchat.hooks.items.moments.MomentsAutoForwardSettings
import h.Hchat.hooks.items.moments.MomentsAutoRefreshFeature
import h.Hchat.hooks.items.moments.MomentsAutoRefreshSettings
import h.Hchat.hooks.items.moments.MomentsBottomDetailFeature
import h.Hchat.hooks.items.moments.MomentsBottomDetailSettings
import h.Hchat.hooks.items.moments.MomentsContactFilterFeature
import h.Hchat.hooks.items.moments.MomentsContactFilterSettings
import h.Hchat.hooks.items.moments.MomentsKeywordBlockFeature
import h.Hchat.hooks.items.moments.MomentsKeywordBlockSettings
import h.Hchat.hooks.items.moments.MomentsPostNotificationFeature
import h.Hchat.hooks.items.moments.MomentsPostNotificationSettings
import h.Hchat.hooks.items.moments.MomentsUploadTailFeature
import h.Hchat.hooks.items.moments.MomentsUploadTailSettings
import h.Hchat.hooks.items.moments.RemoveMomentsAdsFeature
import h.Hchat.hooks.items.moments.RemoveMomentsAdsSettings
import h.Hchat.hooks.items.moments.SnsAntiRecallFeature
import h.Hchat.hooks.items.moments.SnsAntiRecallSettings
import h.Hchat.hooks.items.momentsfake.MomentsFakeCommentSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeForwardRuntimeRegistry
import h.Hchat.hooks.items.momentsfake.MomentsFakeForwardSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeLikeSettingsProvider
import h.Hchat.hooks.items.momentsfake.MomentsFakeLikeCandidateRepository
import h.Hchat.hooks.items.momentsfake.MomentsFakeInteractionRuntimeRegistry
import h.Hchat.hooks.items.momentsfake.MomentsFakeInteractionSettings
import h.Hchat.hooks.items.multirecall.MultiRecallFeature
import h.Hchat.hooks.items.multirecall.MultiRecallSettings
import h.Hchat.hooks.items.payment.core.AutoRedPacketFeature
import h.Hchat.hooks.items.payment.core.PaymentTemplateTimeFormatter
import h.Hchat.hooks.items.payment.core.RedPacketReplyStep
import h.Hchat.hooks.items.payment.core.RedPacketRuleBinding
import h.Hchat.hooks.items.payment.core.RedPacketRuleConfig
import h.Hchat.hooks.items.payment.core.RedPacketRuleTemplate
import h.Hchat.hooks.items.payment.core.RedPacketSettings
import h.Hchat.hooks.items.payment.fakebalance.FakeWalletBalanceFeature
import h.Hchat.hooks.items.payment.fakebalance.FakeWalletBalanceSettings
import h.Hchat.hooks.items.payment.gift.AutoGiftFeature
import h.Hchat.hooks.items.payment.gift.GiftSettings
import h.Hchat.hooks.items.payment.transfer.AutoTransferFeature
import h.Hchat.hooks.items.payment.transfer.AutoTransferSettings
import h.Hchat.hooks.items.payment.transfer.TransferReceiveAccountStore
import h.Hchat.hooks.items.payment.transfer.TransferRuleBinding
import h.Hchat.hooks.items.payment.transfer.TransferRuleConfig
import h.Hchat.hooks.items.payment.transfer.TransferRuleTemplate
import h.Hchat.hooks.items.patblock.PatBlockFeature
import h.Hchat.hooks.items.patblock.PatBlockSettings
import h.Hchat.hooks.items.profileid.ProfileIdFeature
import h.Hchat.hooks.items.profileid.ProfileIdSettings
import h.Hchat.hooks.items.protobuf.ProtobufPacketFeature
import h.Hchat.hooks.items.protobuf.ProtobufPacketRuntime
import h.Hchat.hooks.items.protobuf.ProtobufPacketSettings
import h.Hchat.hooks.items.quoteclear.QuoteDeleteClearFeature
import h.Hchat.hooks.items.quoteclear.QuoteDeleteClearSettings
import h.Hchat.hooks.items.quickcontactedit.QuickContactEditFeature
import h.Hchat.hooks.items.quickcontactedit.QuickContactEditSettings
import h.Hchat.hooks.items.quickmoments.QuickMomentsFeature
import h.Hchat.hooks.items.quickmoments.QuickMomentsSettings
import h.Hchat.hooks.items.quickread.QuickMarkReadFeature
import h.Hchat.hooks.items.quickread.QuickMarkReadSettings
import h.Hchat.hooks.items.quickterminate.QuickTerminateFeature
import h.Hchat.hooks.items.quickterminate.QuickTerminateSettings
import h.Hchat.hooks.items.realtail.RealNameTailFeature
import h.Hchat.hooks.items.realtail.RealNameTailSettings
import h.Hchat.hooks.items.realtail.RealNameTailStore
import h.Hchat.hooks.items.roundavatar.RoundAvatarFeature
import h.Hchat.hooks.items.roundavatar.RoundAvatarSettings
import h.Hchat.hooks.items.script.ScriptPluginFeature
import h.Hchat.hooks.items.script.ScriptPluginManager
import h.Hchat.hooks.items.script.ScriptPluginRuntime
import h.Hchat.hooks.items.script.ScriptPluginSettings
import h.Hchat.hooks.items.script.market.PluginMarketHistoryVersion
import h.Hchat.hooks.items.script.market.PluginMarketInstaller
import h.Hchat.hooks.items.script.market.PluginMarketException
import h.Hchat.hooks.items.script.market.PluginMarketFile
import h.Hchat.hooks.items.script.market.PluginMarketComment
import h.Hchat.hooks.items.script.market.PluginMarketNotification
import h.Hchat.hooks.items.script.market.PluginMarketPlugin
import h.Hchat.hooks.items.script.market.PluginMarketRepository
import h.Hchat.hooks.items.script.market.PluginMarketReviewStatus
import h.Hchat.hooks.items.script.market.PluginMarketUserIdentity
import h.Hchat.hooks.items.script.agent.CodexSettings
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentClient
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentAttachment
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentChatMessage
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentConfig
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentCancellation
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentContext
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentDiff
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentDraft
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentEventIds
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentLocalFiles
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentMcpServer
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentReasoning
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentRequest
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentResumeState
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSession
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSessionStore
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSettings
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentProfile
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentQuotedMessage
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentSpeech
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentTextMerge
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentToolEvent
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentToolResultStore
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentValidator
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceChange
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceToolConfirmation
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceWriteDecision
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWorkspaceTools
import h.Hchat.hooks.items.script.agent.ScriptPluginAgentWriter
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskContentItem
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskFeature
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskItem
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskRuntimeCoordinator
import h.Hchat.hooks.items.scheduledtask.ScheduledTaskSettings
import h.Hchat.hooks.items.selectedmessages.SelectedMessageSnapshot
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesFeature
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesRuntimeCoordinator
import h.Hchat.hooks.items.selectedmessages.SelectedMessagesSettings
import h.Hchat.hooks.items.settings.PluginAgentEntryProvider
import h.Hchat.hooks.items.settings.SettingsEntrySettings
import h.Hchat.hooks.items.settings.SettingsFeature
import h.Hchat.hooks.items.shortvideo.FinderMediaDownloadFeature
import h.Hchat.hooks.items.shortvideo.FinderMediaDownloadSettings
import h.Hchat.hooks.items.statuslimit.StatusTextLimitFeature
import h.Hchat.hooks.items.statuslimit.StatusTextLimitSettings
import h.Hchat.hooks.items.swipequote.SwipeQuoteFeature
import h.Hchat.hooks.items.swipequote.SwipeQuoteSettings
import h.Hchat.hooks.items.tablet.WeChatTabletFeature
import h.Hchat.hooks.items.tablet.WeChatTabletSettings
import h.Hchat.hooks.items.textspeech.TextSpeechFeature
import h.Hchat.hooks.items.textspeech.TextSpeechEngineCatalog
import h.Hchat.hooks.items.textspeech.TextSpeechSettings
import h.Hchat.hooks.items.textspeech.TextSpeechVoiceCatalog
import h.Hchat.hooks.items.textvoice.TextVoiceFeature
import h.Hchat.hooks.items.textvoice.TextVoiceSettings
import h.Hchat.hooks.items.typingreport.TypingReportBlockFeature
import h.Hchat.hooks.items.typingreport.TypingReportBlockSettings
import h.Hchat.hooks.items.voiceforward.VoiceForwardFeature
import h.Hchat.hooks.items.voiceforward.VoiceForwardSettings
import h.Hchat.hooks.items.hchatextra.GroupMemberHistorySettingsProvider
import h.Hchat.hooks.items.hchatextra.MessageDetailsSettingsProvider
import h.Hchat.hooks.items.hchatextra.RedPacketDetailsSettingsProvider
import h.Hchat.hooks.items.hchatextra.SkipWebRiskSettingsProvider
import h.Hchat.hooks.items.hchatextra.HchatExtraSettings
import h.Hchat.hooks.items.voicepreview.VoicePreviewFeature
import h.Hchat.hooks.items.voicepreview.VoicePreviewSettings
import h.Hchat.media.AudioTransformBridge
import h.Hchat.preferences.HchatStorage
import h.Hchat.preferences.TermsGate
import h.Hchat.ui.FeatureSettingsProvider
import h.Hchat.ui.UIRegistry
import h.Hchat.utils.KavaReflector
import h.Hchat.utils.KeywordReplacementRule
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import org.json.JSONArray
import org.json.JSONObject
import top.yukonga.miuix.kmp.basic.BreadcrumbBar
import top.yukonga.miuix.kmp.basic.BreadcrumbItem
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.Checkbox
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.ScrollBehavior
import top.yukonga.miuix.kmp.basic.Slider
import top.yukonga.miuix.kmp.basic.TabRow
import top.yukonga.miuix.kmp.basic.DropdownEntry
import top.yukonga.miuix.kmp.basic.DropdownItem
import top.yukonga.miuix.kmp.menu.WindowDropdownMenu
import top.yukonga.miuix.kmp.basic.CircularProgressIndicator
import top.yukonga.miuix.kmp.basic.SmallTitle
import top.yukonga.miuix.kmp.basic.Switch
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.nav.core.NavDisplay
import top.yukonga.miuix.kmp.nav.core.NavKey
import top.yukonga.miuix.kmp.nav.core.navBackStackOf
import top.yukonga.miuix.kmp.nav.transition.NavTransitions
import top.yukonga.miuix.kmp.utils.springAnimateToPage
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.blur.Backdrop
import top.yukonga.miuix.kmp.blur.layerBackdrop
import top.yukonga.miuix.kmp.blur.rememberLayerBackdrop
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.darkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme
import top.yukonga.miuix.kmp.window.WindowDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.File
import java.lang.ref.WeakReference
import java.net.URL
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Calendar
import java.util.LinkedHashSet
import java.util.Locale
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.abs
import kotlin.random.Random

@Composable
internal fun MessageBlockMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, MessageBlockSettings.PREFS_NAME) }
    val initialState = remember { initialMessageBlockState(sp) }
    var templates by remember {
        mutableStateOf(initialState.templates)
    }
    var bindings by remember {
        mutableStateOf(initialState.bindings)
    }
    var defaultPrivateRule by remember { mutableStateOf(MessageBlockSettings(context).defaultPrivateRule()) }
    var defaultGroupRule by remember { mutableStateOf(MessageBlockSettings(context).defaultGroupRule()) }
    var defaultOfficialRule by remember { mutableStateOf(MessageBlockSettings(context).defaultOfficialRule()) }
    var templateEditor by remember { mutableStateOf<MessageBlockTemplateEditorRequest?>(null) }
    var bindingEditor by remember { mutableStateOf<MessageBlockBindingEditorRequest?>(null) }
    var batchBindingEditor by remember { mutableStateOf<MessageBlockBatchBindingEditorRequest?>(null) }
    var bindingPicker by remember { mutableStateOf<ContactPickerRequest?>(null) }
    var groupMemberBindingPicker by remember { mutableStateOf<GroupMemberPickerRequest?>(null) }
    var defaultRuleEditor by remember { mutableStateOf<MessageBlockDefaultRuleKind?>(null) }
    var showTemplateManager by remember { mutableStateOf(false) }
    var showListManager by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    LaunchedEffect(Unit) {
        if (initialState.shouldPersist) {
            sp.edit()
                .putString(MessageBlockSettings.KEY_TEMPLATES, MessageBlockSettings.encodeTemplates(templates))
                .putString(MessageBlockSettings.KEY_BINDINGS, MessageBlockSettings.encodeBindings(bindings))
                .apply()
        }
    }

    val route = when {
        templateEditor != null -> MessageBlockRoute.TemplateEditor(templateEditor!!)
        bindingEditor != null -> MessageBlockRoute.BindingEditor(bindingEditor!!)
        batchBindingEditor != null -> MessageBlockRoute.BatchBindingEditor(batchBindingEditor!!)
        bindingPicker != null -> MessageBlockRoute.ContactPicker(bindingPicker!!)
        groupMemberBindingPicker != null -> MessageBlockRoute.GroupMemberPicker(groupMemberBindingPicker!!)
        defaultRuleEditor != null -> MessageBlockRoute.DefaultRuleEditor(defaultRuleEditor!!)
        showTemplateManager -> MessageBlockRoute.TemplateManager
        showListManager -> MessageBlockRoute.ListManager
        else -> MessageBlockRoute.Main
    }

    SettingsRouteTransition(
        targetState = route,
        label = "MessageBlockRouteTransition",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            is MessageBlockRoute.TemplateEditor -> {
                val request = currentRoute.request
                MessageBlockTemplatePage(
                    context = context,
                    request = request,
                    onBack = { templateEditor = null },
                    onSave = { updated ->
                        val next = if (request.index in templates.indices) {
                            templates.toMutableList().also { it[request.index] = MessageBlockSettings.clearTemplateTargets(updated) }
                        } else {
                            templates + MessageBlockSettings.clearTemplateTargets(updated)
                        }
                        templates = next
                        sp.edit().putString(MessageBlockSettings.KEY_TEMPLATES, MessageBlockSettings.encodeTemplates(next)).apply()
                        templateEditor = null
                    },
                    onDelete = {
                        if (request.index in templates.indices) {
                            val deletedId = templates[request.index].id
                            val next = templates.toMutableList().also { it.removeAt(request.index) }
                            val nextBindings = bindings.map { binding ->
                                binding.copy(templateIds = binding.templateIds - deletedId)
                            }
                            fun withoutDeletedTemplate(rule: MessageBlockDefaultRule): MessageBlockDefaultRule {
                                val nextIds = rule.templateIds - deletedId
                                return rule.copy(
                                    enabled = rule.enabled && (rule.customRules || nextIds.isNotEmpty()),
                                    templateIds = nextIds
                                )
                            }
                            val nextDefaultPrivate = withoutDeletedTemplate(defaultPrivateRule)
                            val nextDefaultGroup = withoutDeletedTemplate(defaultGroupRule)
                            val nextDefaultOfficial = withoutDeletedTemplate(defaultOfficialRule)
                            templates = next
                            bindings = nextBindings
                            defaultPrivateRule = nextDefaultPrivate
                            defaultGroupRule = nextDefaultGroup
                            defaultOfficialRule = nextDefaultOfficial
                            sp.edit()
                                .putString(MessageBlockSettings.KEY_TEMPLATES, MessageBlockSettings.encodeTemplates(next))
                                .putString(MessageBlockSettings.KEY_BINDINGS, MessageBlockSettings.encodeBindings(nextBindings))
                                .putString(
                                    MessageBlockSettings.KEY_DEFAULT_PRIVATE,
                                    MessageBlockSettings.encodeDefaultRule(nextDefaultPrivate, group = false)
                                )
                                .putString(
                                    MessageBlockSettings.KEY_DEFAULT_GROUP,
                                    MessageBlockSettings.encodeDefaultRule(nextDefaultGroup, group = true)
                                )
                                .putString(
                                    MessageBlockSettings.KEY_DEFAULT_OFFICIAL,
                                    MessageBlockSettings.encodeDefaultRule(
                                        nextDefaultOfficial,
                                        group = false,
                                        official = true
                                    )
                                )
                                .apply()
                        }
                        templateEditor = null
                    }
                )
            }
            is MessageBlockRoute.BindingEditor -> {
                val request = currentRoute.request
                MessageBlockBindingPage(
                    context = context,
                    request = request,
                    templates = templates,
                    onBack = { bindingEditor = null },
                    onSave = { updated ->
                        val normalized = normalizedMessageBlockBinding(updated)
                        val base = if (request.index in bindings.indices) {
                            bindings.toMutableList().also { it.removeAt(request.index) }
                        } else {
                            bindings
                        }
                        val next = upsertMessageBlockBindings(base, listOf(normalized))
                        bindings = next
                        sp.edit().putString(MessageBlockSettings.KEY_BINDINGS, MessageBlockSettings.encodeBindings(next)).apply()
                        bindingEditor = null
                    },
                    onDelete = {
                        if (request.index in bindings.indices) {
                            val next = bindings.toMutableList().also { it.removeAt(request.index) }
                            bindings = next
                            sp.edit().putString(MessageBlockSettings.KEY_BINDINGS, MessageBlockSettings.encodeBindings(next)).apply()
                        }
                        bindingEditor = null
                    }
                )
            }
            is MessageBlockRoute.BatchBindingEditor -> {
                val request = currentRoute.request
                MessageBlockBatchBindingPage(
                    context = context,
                    request = request,
                    templates = templates,
                    onBack = { batchBindingEditor = null },
                    onSave = { updated ->
                        val next = upsertMessageBlockBindings(bindings, updated)
                        bindings = next
                        sp.edit().putString(MessageBlockSettings.KEY_BINDINGS, MessageBlockSettings.encodeBindings(next)).apply()
                        Toast.makeText(context, "已保存 ${updated.size} 个名单项", Toast.LENGTH_SHORT).show()
                        batchBindingEditor = null
                    }
                )
            }
            is MessageBlockRoute.DefaultRuleEditor -> {
                val rule = when (currentRoute.kind) {
                    MessageBlockDefaultRuleKind.PRIVATE -> defaultPrivateRule
                    MessageBlockDefaultRuleKind.GROUP -> defaultGroupRule
                    MessageBlockDefaultRuleKind.OFFICIAL -> defaultOfficialRule
                }
                MessageBlockDefaultRulePage(
                    context = context,
                    rule = rule,
                    templates = templates,
                    onBack = { defaultRuleEditor = null },
                    onSave = { updated ->
                        val normalized = when (currentRoute.kind) {
                            MessageBlockDefaultRuleKind.PRIVATE -> updated.copy(group = false, official = false, label = "默认私聊规则")
                            MessageBlockDefaultRuleKind.GROUP -> updated.copy(group = true, official = false, label = "默认群聊规则")
                            MessageBlockDefaultRuleKind.OFFICIAL -> updated.copy(group = false, official = true, label = "默认公众号规则")
                        }
                        when (currentRoute.kind) {
                            MessageBlockDefaultRuleKind.PRIVATE -> {
                                defaultPrivateRule = normalized
                                MessageBlockSettings(context).saveDefaultPrivateRule(normalized)
                            }
                            MessageBlockDefaultRuleKind.GROUP -> {
                                defaultGroupRule = normalized
                                MessageBlockSettings(context).saveDefaultGroupRule(normalized)
                            }
                            MessageBlockDefaultRuleKind.OFFICIAL -> {
                                defaultOfficialRule = normalized
                                MessageBlockSettings(context).saveDefaultOfficialRule(normalized)
                            }
                        }
                        Toast.makeText(context, "默认规则已保存", Toast.LENGTH_SHORT).show()
                        defaultRuleEditor = null
                    }
                )
            }
            is MessageBlockRoute.ContactPicker -> {
                val request = currentRoute.request
                ContactPickerPage(
                    context = context,
                    request = request,
                    onBack = { bindingPicker = null },
                    onConfirm = { selected ->
                        val additions = selected.map {
                            messageBlockBindingFromContact(
                                option = it,
                                templates = templates,
                                existing = findMessageBlockBinding(bindings, MessageBlockSettings.TARGET_CONTACT, it.id)
                            )
                        }
                        if (additions.size == 1) {
                            val existingIndex = bindings.indexOfFirst {
                                it.targetType == additions.first().targetType &&
                                    it.targetId == additions.first().targetId
                            }
                            bindingEditor = MessageBlockBindingEditorRequest(
                                index = existingIndex.takeIf { it >= 0 } ?: bindings.size,
                                binding = additions.first(),
                                canDelete = existingIndex >= 0
                            )
                        } else if (additions.isNotEmpty()) {
                            batchBindingEditor = MessageBlockBatchBindingEditorRequest(
                                title = "批量添加名单",
                                bindings = additions
                            )
                        }
                        bindingPicker = null
                    }
                )
            }
            is MessageBlockRoute.GroupMemberPicker -> {
                val request = currentRoute.request
                GroupMemberPickerPage(
                    context = context,
                    request = request,
                    onBack = { groupMemberBindingPicker = null },
                    onConfirm = { selected ->
                        val additions = selected.map {
                            messageBlockBindingFromGroupMember(
                                entry = it,
                                templates = templates,
                                existing = findMessageBlockBinding(bindings, MessageBlockSettings.TARGET_GROUP_MEMBER, it)
                            )
                        }
                        if (additions.size == 1) {
                            val binding = additions.first()
                            val existingIndex = bindings.indexOfFirst {
                                it.targetType == binding.targetType &&
                                    it.targetId == binding.targetId
                            }
                            bindingEditor = MessageBlockBindingEditorRequest(
                                index = existingIndex.takeIf { it >= 0 } ?: bindings.size,
                                binding = binding,
                                canDelete = existingIndex >= 0
                            )
                        } else if (additions.isNotEmpty()) {
                            batchBindingEditor = MessageBlockBatchBindingEditorRequest(
                                title = "批量添加群成员",
                                bindings = additions
                            )
                        }
                        groupMemberBindingPicker = null
                    }
                )
            }
            MessageBlockRoute.TemplateManager -> {
                MessageBlockTemplateListPage(
                    templates = templates,
                    onBack = { showTemplateManager = false },
                    onOpenTemplate = { index, template ->
                        templateEditor = MessageBlockTemplateEditorRequest(index, template, canDelete = true)
                    },
                    onAddTemplate = {
                        templateEditor = MessageBlockTemplateEditorRequest(
                            templates.size,
                            newMessageBlockTemplate(templates.size + 1),
                            canDelete = false
                        )
                    }
                )
            }
            MessageBlockRoute.ListManager -> {
                MessageBlockListManagerPage(
                    bindings = bindings,
                    templates = templates,
                    onBack = { showListManager = false },
                    onOpenBinding = { index, binding ->
                        bindingEditor = MessageBlockBindingEditorRequest(index, binding, canDelete = true)
                    },
                    onAddContact = {
                        bindingPicker = ContactPickerRequest(
                            title = "选择名单",
                            mode = ContactPickerMode.ALL_CHATS,
                            multiSelect = true,
                            existingValue = "",
                            onValue = {},
                            enableLabels = true
                        )
                    },
                    onAddGroupMember = {
                        groupMemberBindingPicker = GroupMemberPickerRequest(
                            title = "选择群成员",
                            existingValue = "",
                            onValue = {}
                        )
                    },
                    onDeleteBindings = { targets ->
                        if (targets.isEmpty()) {
                            Toast.makeText(context, "没有可删除的名单", Toast.LENGTH_SHORT).show()
                        } else {
                            val removeKeys = targets.map {
                                MessageBlockSettings.bindingKey(it.targetType, it.targetId)
                            }.toSet()
                            val next = bindings.filterNot {
                                removeKeys.contains(MessageBlockSettings.bindingKey(it.targetType, it.targetId))
                            }
                            bindings = next
                            sp.edit().putString(MessageBlockSettings.KEY_BINDINGS, MessageBlockSettings.encodeBindings(next)).apply()
                            Toast.makeText(context, "已删除 ${targets.size} 个名单项", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            MessageBlockRoute.Main -> PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar("返回", onBack)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "基础") }
            item {
                SettingsCard {
                    SwitchRow(
                        sp,
                        MessageBlockSettings.KEY_ENABLE,
                        "启用屏蔽消息",
                        "总开关开启后，仅命中启用模板的新消息会被拦截",
                        MessageBlockSettings.DEFAULT_ENABLE
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "管理") }
            item {
                SettingsCard {
                    SelectRow(
                        title = "默认私聊规则",
                        summary = describeMessageBlockDefaultRule(defaultPrivateRule, templates),
                        onClick = { defaultRuleEditor = MessageBlockDefaultRuleKind.PRIVATE }
                    )
                    InsetDivider()
                    SelectRow(
                        title = "默认群聊规则",
                        summary = describeMessageBlockDefaultRule(defaultGroupRule, templates),
                        onClick = { defaultRuleEditor = MessageBlockDefaultRuleKind.GROUP }
                    )
                    InsetDivider()
                    SelectRow(
                        title = "默认公众号规则",
                        summary = describeMessageBlockDefaultRule(defaultOfficialRule, templates),
                        onClick = { defaultRuleEditor = MessageBlockDefaultRuleKind.OFFICIAL }
                    )
                    InsetDivider()
                    SelectRow(
                        title = "模板管理",
                        summary = if (templates.isEmpty()) "暂无模板，进入后添加屏蔽规则" else "${templates.size} 个模板，进入后添加或修改规则",
                        onClick = { showTemplateManager = true }
                    )
                    InsetDivider()
                    SelectRow(
                        title = "名单管理",
                        summary = if (bindings.isEmpty()) "暂无名单，进入后添加好友、群聊、公众号或群成员" else "${bindings.size} 个名单项，进入后分配模板",
                        onClick = { showListManager = true }
                    )
                }
            }
        }
    }
        }
    }
}

@Composable
internal fun MessageBlockTemplateListPage(
    templates: List<MessageBlockTemplate>,
    onBack: () -> Unit,
    onOpenTemplate: (Int, MessageBlockTemplate) -> Unit,
    onAddTemplate: () -> Unit
) {
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "模板管理",
        largeTitle = "模板管理",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "新增模板",
                onPrimaryClick = onAddTemplate,
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "模板") }
            item {
                SettingsCard {
                    if (templates.isEmpty()) {
                        EmptyText("暂无模板。模板只配置消息类型和关键词，名单在“名单管理”里分配模板。")
                    } else {
                        templates.forEachIndexed { index, template ->
                            SelectRow(
                                title = template.name.ifBlank { "模板 ${index + 1}" },
                                summary = describeMessageBlockTemplate(template),
                                onClick = { onOpenTemplate(index, template) }
                            )
                            if (index < templates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun MessageBlockListManagerPage(
    bindings: List<MessageBlockBinding>,
    templates: List<MessageBlockTemplate>,
    onBack: () -> Unit,
    onOpenBinding: (Int, MessageBlockBinding) -> Unit,
    onAddContact: () -> Unit,
    onAddGroupMember: () -> Unit,
    onDeleteBindings: (List<MessageBlockBinding>) -> Unit
) {
    val context = LocalContext.current
    var category by remember { mutableStateOf(ConversationRuleCategory.ALL) }
    var query by remember { mutableStateOf("") }
    var batchDeleteMode by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    val lower = query.trim().lowercase(Locale.US)
    val visibleBindings = bindings.mapIndexed { index, binding -> index to binding }
        .filter { (_, binding) ->
            conversationRuleCategoryMatches(binding, category) &&
                (lower.isEmpty() || messageBlockBindingMatchesQuery(binding, templates, lower))
        }
    val listTitle = when {
        bindings.isEmpty() -> "名单"
        lower.isEmpty() -> "名单 · ${bindings.size} 项"
        else -> "名单 · ${visibleBindings.size}/${bindings.size} 项"
    }
    val visibleIds = visibleBindings.mapTo(LinkedHashSet()) { it.second.id }
    val allVisibleSelected = visibleIds.isNotEmpty() && visibleIds.all { it in selectedIds }
    val selectedBindings = bindings.filter { it.id in selectedIds }
    PageScaffold(
        title = "名单管理",
        largeTitle = "名单管理",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            if (batchDeleteMode) {
                BottomActionBar(
                    primaryText = "删除所选（${selectedBindings.size}）",
                    onPrimaryClick = {
                        if (selectedBindings.isEmpty()) {
                            Toast.makeText(context, "请先选择名单", Toast.LENGTH_SHORT).show()
                        } else {
                            showDeleteConfirm = true
                        }
                    },
                    secondaryText = "取消",
                    onSecondaryClick = {
                        batchDeleteMode = false
                        selectedIds = emptySet()
                    },
                    middleText = if (visibleIds.isEmpty()) null else if (allVisibleSelected) "取消全选" else "全选",
                    onMiddleClick = if (visibleIds.isEmpty()) null else {
                        {
                            selectedIds = if (allVisibleSelected) {
                                selectedIds - visibleIds
                            } else {
                                selectedIds + visibleIds
                            }
                        }
                    }
                )
            } else {
                BottomActionBar(
                    primaryText = "返回",
                    onPrimaryClick = onBack,
                    middleText = if (bindings.isEmpty()) null else "批量删除",
                    onMiddleClick = if (bindings.isEmpty()) null else {
                        {
                            batchDeleteMode = true
                            selectedIds = emptySet()
                        }
                    }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    ConversationRuleCategoryTabs(
                        selected = category,
                        onSelected = { category = it }
                    )
                }
            }
            item {
                SettingsCard {
                    InputRow(
                        title = "搜索名单",
                        summary = "昵称 / wxid / 群号 / 模板名",
                        value = query,
                        onValueChange = { query = it }
                    )
                }
            }
            if (!batchDeleteMode) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "添加") }
                item {
                    SettingsCard {
                        ActionRow("添加好友/群聊/公众号", "批量添加后可一次选择模板") {
                            onAddContact()
                        }
                        InsetDivider()
                        ActionRow("添加群成员", "批量添加后可一次选择模板") {
                            onAddGroupMember()
                        }
                    }
                }
            }
            item { SmallTitle(text = listTitle) }
            item {
                SettingsCard {
                    if (bindings.isEmpty()) {
                        EmptyText("暂无名单。添加好友、群聊、公众号或群成员后，再给名单分配模板。")
                    } else if (visibleBindings.isEmpty()) {
                        EmptyText("没有匹配名单。可按昵称、ID 或模板名搜索。")
                    } else {
                        visibleBindings.forEachIndexed { rowIndex, (index, binding) ->
                            if (batchDeleteMode) {
                                OptionChoiceRow(
                                    item = OptionItem(
                                        label = binding.label.ifBlank { binding.targetId },
                                        value = index,
                                        summary = describeMessageBlockBinding(binding, templates)
                                    ),
                                    selected = binding.id in selectedIds,
                                    onClick = {
                                        selectedIds = if (binding.id in selectedIds) {
                                            selectedIds - binding.id
                                        } else {
                                            selectedIds + binding.id
                                        }
                                    }
                                )
                            } else {
                                SelectRow(
                                    title = binding.label.ifBlank { binding.targetId },
                                    summary = describeMessageBlockBinding(binding, templates),
                                    onClick = { onOpenBinding(index, binding) }
                                )
                            }
                            if (rowIndex < visibleBindings.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
    BatchDeleteConfirmDialog(
        show = showDeleteConfirm,
        message = "将删除已选的 ${selectedBindings.size} 个名单项，此操作不可撤销。",
        labels = selectedBindings.map { it.label.ifBlank { it.targetId } },
        onDismiss = { showDeleteConfirm = false },
        onConfirm = {
            val targets = selectedBindings
            showDeleteConfirm = false
            batchDeleteMode = false
            selectedIds = emptySet()
            onDeleteBindings(targets)
        }
    )
}

@Composable
internal fun MessageBlockBatchBindingPage(
    context: Context,
    request: MessageBlockBatchBindingEditorRequest,
    templates: List<MessageBlockTemplate>,
    onBack: () -> Unit,
    onSave: (List<MessageBlockBinding>) -> Unit
) {
    var enabled by remember(request) { mutableStateOf(true) }
    var action by remember(request) { mutableStateOf(MessageBlockSettings.ACTION_BLOCK) }
    var templateIds by remember(request) { mutableStateOf(defaultMessageBlockTemplateIds(templates)) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = request.title,
        largeTitle = request.title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存名单",
                onPrimaryClick = {
                    if (templateIds.isEmpty()) {
                        Toast.makeText(context, "请先选择模板", Toast.LENGTH_SHORT).show()
                    } else {
                        onSave(
                            request.bindings.map {
                                it.copy(
                                    enabled = enabled,
                                    action = action,
                                    templateIds = templateIds,
                                    customRules = false,
                                    typeAll = false,
                                    types = emptySet(),
                                    textKeywords = ""
                                )
                            }
                        )
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "批量配置") }
            item {
                SettingsCard {
                    SelectRow(
                        title = "已选择",
                        summary = "${request.bindings.size} 个名单项",
                        onClick = {}
                    )
                    InsetDivider()
                    SwitchRow(
                        checked = enabled,
                        title = "启用名单",
                        summary = "关闭后保留名单但不参与匹配",
                        onCheckedChange = { enabled = it }
                    )
                    InsetDivider()
                    SwitchRow(
                        checked = action == MessageBlockSettings.ACTION_EXCLUDE,
                        title = "排除名单",
                        summary = "开启后这些名单命中时跳过屏蔽",
                        onCheckedChange = {
                            action = if (it) MessageBlockSettings.ACTION_EXCLUDE else MessageBlockSettings.ACTION_BLOCK
                        }
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模板") }
            item {
                SettingsCard {
                    if (templates.isEmpty()) {
                        EmptyText("暂无模板。先新增模板，再回来批量添加名单。")
                    } else {
                        templates.forEachIndexed { index, template ->
                            SwitchRow(
                                checked = templateIds.contains(template.id),
                                title = template.name.ifBlank { "模板 ${index + 1}" },
                                summary = describeMessageBlockTemplate(template),
                                onCheckedChange = { checked ->
                                    templateIds = if (checked) {
                                        templateIds + template.id
                                    } else {
                                        templateIds - template.id
                                    }
                                }
                            )
                            if (index < templates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "名单") }
            item {
                SettingsCard {
                    request.bindings.take(30).forEachIndexed { index, binding ->
                        SelectRow(
                            title = binding.label.ifBlank { binding.targetId },
                            summary = messageBlockBindingTargetSummary(binding),
                            onClick = {}
                        )
                        if (index < minOf(request.bindings.size, 30) - 1) InsetDivider()
                    }
                    if (request.bindings.size > 30) {
                        InsetDivider()
                        EmptyText("还有 ${request.bindings.size - 30} 个名单项未展开显示")
                    }
                }
            }
        }
    }
}

@Composable
internal fun MessageBlockDefaultRulePage(
    context: Context,
    rule: MessageBlockDefaultRule,
    templates: List<MessageBlockTemplate>,
    onBack: () -> Unit,
    onSave: (MessageBlockDefaultRule) -> Unit
) {
    var enabled by remember(rule) { mutableStateOf(rule.enabled) }
    var templateIds by remember(rule) { mutableStateOf(rule.templateIds) }
    var customRules by remember(rule) { mutableStateOf(rule.customRules) }
    var typeAll by remember(rule) { mutableStateOf(rule.typeAll) }
    var types by remember(rule) { mutableStateOf(rule.types) }
    var textKeywords by remember(rule) { mutableStateOf(rule.textKeywords) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    val title = when {
        rule.official -> "默认公众号规则"
        rule.group -> "默认群聊规则"
        else -> "默认私聊规则"
    }
    val scopeSummary = when {
        rule.official -> "未配置名单的公众号按此规则屏蔽"
        rule.group -> "未配置名单的群聊按此规则屏蔽"
        else -> "未配置名单的私聊按此规则屏蔽"
    }
    fun copySelectedTemplateRules() {
        val rules = messageBlockRuleStateFromTemplates(templateIds, templates)
        typeAll = rules.typeAll
        types = rules.types
        textKeywords = rules.textKeywords
    }

    PageScaffold(
        title = title,
        largeTitle = title,
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存规则",
                onPrimaryClick = {
                    if (enabled && !customRules && templateIds.isEmpty()) {
                        Toast.makeText(context, "请先选择模板", Toast.LENGTH_SHORT).show()
                    } else {
                        onSave(
                            rule.copy(
                                enabled = enabled,
                                templateIds = templateIds,
                                customRules = customRules,
                                typeAll = typeAll,
                                types = types,
                                textKeywords = if (customRules && !typeAll && types.contains(MessageBlockSettings.TYPE_TEXT)) textKeywords else ""
                            )
                        )
                    }
                },
                secondaryText = "返回",
                onSecondaryClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            state = listState,
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "默认规则") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = enabled,
                        title = "启用默认规则",
                        summary = scopeSummary,
                        onCheckedChange = { enabled = it }
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模板") }
            item {
                SettingsCard {
                    if (templates.isEmpty()) {
                        EmptyText("暂无模板。先新增模板，或开启下方专属规则。")
                    } else {
                        templates.forEachIndexed { index, template ->
                            SwitchRow(
                                checked = templateIds.contains(template.id),
                                title = template.name.ifBlank { "模板 ${index + 1}" },
                                summary = describeMessageBlockTemplate(template),
                                onCheckedChange = { checked ->
                                    templateIds = if (checked) {
                                        templateIds + template.id
                                    } else {
                                        templateIds - template.id
                                    }
                                    if (!customRules) copySelectedTemplateRules()
                                }
                            )
                            if (index < templates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "规则") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = customRules,
                        title = "默认专属规则",
                        summary = "开启后不依赖模板，直接按下方类型和关键词屏蔽",
                        onCheckedChange = { checked ->
                            customRules = checked
                            if (checked && !typeAll && types.isEmpty() && textKeywords.isBlank()) {
                                copySelectedTemplateRules()
                            }
                        }
                    )
                    if (customRules) {
                        InsetDivider()
                        ActionRow("套用已选模板规则", "把上方模板的类型和关键词复制到这里") {
                            copySelectedTemplateRules()
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = typeAll,
                            title = "所有消息",
                            summary = "开启后默认范围内全部类型生效",
                            onCheckedChange = {
                                typeAll = it
                                if (it) textKeywords = ""
                            }
                        )
                        if (!typeAll) {
                            messageBlockTypeOptions().forEach { option ->
                                InsetDivider()
                                SwitchRow(
                                    checked = types.contains(option.key),
                                    title = option.title,
                                    summary = option.summary,
                                    onCheckedChange = { checked ->
                                        types = if (checked) {
                                            types + option.key
                                        } else {
                                            if (option.key == MessageBlockSettings.TYPE_TEXT) textKeywords = ""
                                            types - option.key
                                        }
                                    }
                                )
                            }
                            if (types.contains(MessageBlockSettings.TYPE_TEXT)) {
                                InsetDivider()
                                InputRow(
                                    "文字关键词",
                                    "文字和文字引用消息生效；多个用 |、逗号或换行分隔，留空则全部命中",
                                    textKeywords,
                                    minLines = 2
                                ) { textKeywords = it }
                            }
                        }
                    }
                }
            }
        }
    }
}

