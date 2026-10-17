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

internal object AudioTransformSettingsPage {
    @Composable
    operator fun invoke(
        context: Context,
        provider: FeatureSettingsProvider,
        onBack: () -> Unit
    ) {
    val sp = remember { HchatStorage.preferences(context, AudioTransformSettings.PREFS_NAME) }
    val bridge = remember { AudioTransformBridge() }
    val handler = remember { Handler(Looper.getMainLooper()) }
    val mainListState = rememberLazyListState()
    var route by remember { mutableStateOf<AudioTransformRoute>(AudioTransformRoute.Main) }
    var mode by remember {
        mutableStateOf(
            sp.getInt(
                AudioTransformSettings.KEY_MODE,
                AudioTransformSettings.DEFAULT_MODE
            )
        )
    }
    var inputPath by rememberSaveable { mutableStateOf("") }
    var inputName by rememberSaveable { mutableStateOf("") }
    var selectedTalker by rememberSaveable {
        mutableStateOf(
            sp.getString(AudioTransformSettings.KEY_LAST_TALKER, "").orEmpty()
        )
    }
    var splitEnabled by remember {
        mutableStateOf(
            sp.getBoolean(
                AudioTransformSettings.KEY_SPLIT_ENABLED,
                AudioTransformSettings.DEFAULT_SPLIT_ENABLED
            )
        )
    }
    var splitDurationSeconds by rememberSaveable {
        mutableStateOf(
            sp.getLong(
                AudioTransformSettings.KEY_SPLIT_DURATION_SECONDS,
                AudioTransformSettings.DEFAULT_SPLIT_DURATION_SECONDS
            ).coerceAtLeast(AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS).toString()
        )
    }
    var clipStartMillis by rememberSaveable { mutableStateOf(0L) }
    var clipEndMillis by rememberSaveable { mutableStateOf(0L) }
    val splitCancellation = remember { AtomicBoolean(false) }
    val splitWorker = remember { AtomicReference<Thread?>(null) }
    var running by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }

    DisposableEffect(Unit) {
        onDispose {
            splitCancellation.set(true)
            splitWorker.getAndSet(null)?.interrupt()
        }
    }

    fun updateResult(text: String, toast: Boolean = false) {
        resultText = text
        if (toast && text.isNotBlank()) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun startSend() {
        if (running) return
        val sourcePath = inputPath.takeIf { it.isNotBlank() && File(it).isFile }
        if (sourcePath == null) {
            updateResult("请先选择输入文件", toast = true)
            return
        }
        val talker = selectedTalker.trim()
        if (talker.isEmpty()) {
            updateResult("请先选择聊天", toast = true)
            return
        }
        val validate = audioTransformValidateInput(mode, sourcePath, bridge)
        if (validate != null) {
            updateResult(validate, toast = true)
            return
        }
        val shouldSplit = splitEnabled
        val segmentDuration = if (shouldSplit) splitDurationSeconds.toLongOrNull() else null
        val rangeStart = clipStartMillis.coerceAtLeast(0L)
        val rangeEnd = clipEndMillis.takeIf { it > 0L }
        if (shouldSplit) {
            if (segmentDuration == null) {
                updateResult("请输入有效的每段时长", toast = true)
                return
            }
            if (rangeEnd != null && rangeEnd <= rangeStart) {
                updateResult("结束时间必须晚于开始时间", toast = true)
                return
            }
            if (segmentDuration < AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS) {
                updateResult(
                    "每段时长最少为 ${AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS} 秒",
                    toast = true
                )
                return
            }
        }
        val talkerLabel = audioTransformTalkerSummary(talker)
        splitCancellation.set(false)
        running = true
        resultText = if (shouldSplit) "正在准备切割" else "正在发送到 $talkerLabel"
        val worker = Thread({
            var sentCount = 0
            var totalCount = 0
            val message = try {
                if (!shouldSplit) {
                    val ok = runCatching {
                        WeChatApis.media()?.voices()?.send(talker, sourcePath) == true
                    }.getOrDefault(false)
                    if (ok) "已发送到 $talkerLabel" else "发送失败，请稍后重试"
                } else {
                    AudioTransformSegmenter.prepare(
                        bridge = bridge,
                        sourcePath = sourcePath,
                        cacheDir = context.cacheDir,
                        durationSeconds = segmentDuration!!,
                        startMillis = rangeStart,
                        endMillis = rangeEnd,
                        cancelled = splitCancellation,
                        onProgress = { progress ->
                            handler.post {
                                if (!splitCancellation.get()) resultText = progress
                            }
                        }
                    ).use { batch ->
                        totalCount = batch.segments.size
                        batch.segments.forEachIndexed { index, segment ->
                            if (splitCancellation.get()) throw AudioTransformCancelledException()
                            handler.post {
                                if (!splitCancellation.get()) {
                                    resultText = "正在发送 ${index + 1}/$totalCount"
                                }
                            }
                            val sent = WeChatApis.media()?.voices()?.send(
                                talker,
                                segment.file.absolutePath,
                                segment.durationMillis
                            ) == true
                            if (sent) sentCount++
                            if (splitCancellation.get()) throw AudioTransformCancelledException()
                            if (!sent) {
                                throw IllegalStateException("第 ${index + 1} 段发送失败")
                            }
                            if (sentCount < totalCount) {
                                repeat(5) {
                                    if (splitCancellation.get()) throw AudioTransformCancelledException()
                                    Thread.sleep(100L)
                                }
                            }
                        }
                    }
                    "已发送 $totalCount 段到 $talkerLabel"
                }
            } catch (_: AudioTransformCancelledException) {
                if (totalCount > 0) "已取消，已发送 $sentCount/$totalCount 段" else "已取消"
            } catch (throwable: Throwable) {
                if (splitCancellation.get() || throwable is InterruptedException) {
                    if (totalCount > 0) "已取消，已发送 $sentCount/$totalCount 段" else "已取消"
                } else {
                    val detail = throwable.message ?: "未知错误"
                    if (totalCount > 0) {
                        "发送失败，已发送 $sentCount/$totalCount 段：$detail"
                    } else {
                        "切割发送失败：$detail"
                    }
                }
            }
            if (shouldSplit) splitWorker.compareAndSet(Thread.currentThread(), null)
            handler.post {
                running = false
                updateResult(message, toast = true)
            }
        }, "Hchat-AudioTransform-Send")
        if (shouldSplit) splitWorker.set(worker)
        worker.start()
    }

    fun startSave(outputUri: Uri, outputName: String) {
        if (running) return
        val sourcePath = inputPath.takeIf { it.isNotBlank() && File(it).isFile }
        if (sourcePath == null) {
            updateResult("请先选择输入文件", toast = true)
            return
        }
        val validate = audioTransformValidateInput(mode, sourcePath, bridge)
        if (validate != null) {
            updateResult(validate, toast = true)
            return
        }
        running = true
        resultText = "正在转换并保存"
        Thread({
            val sourceFile = File(sourcePath)
            val tempDir = File(context.cacheDir, "audio_transform_output").apply { mkdirs() }
            val tempFile = File(
                tempDir,
                "audio_${System.currentTimeMillis()}${audioTransformOutputExtension(mode)}"
            )
            val result = runCatching {
                val code = when (mode) {
                    AudioTransformSettings.MODE_AUDIO_TO_SILK_SAVE -> {
                        if (bridge.getFileType(sourcePath) == 1) {
                            sourceFile.copyTo(tempFile, overwrite = true)
                            0
                        } else {
                            bridge.autoToSilk(sourcePath, tempFile.absolutePath, AudioTransformBridge.DEFAULT_HZ)
                        }
                    }
                    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> {
                        bridge.silkToMp3(sourcePath, tempFile.absolutePath, AudioTransformBridge.DEFAULT_HZ)
                    }
                    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> {
                        bridge.silkToM4a(sourcePath, tempFile.absolutePath, AudioTransformBridge.DEFAULT_HZ)
                    }
                    else -> -2
                }
                if (code != 0) {
                    false to "转换失败：${bridge.getErrorMessage(code)}"
                } else if (!tempFile.isFile || tempFile.length() <= 0L) {
                    false to "转换失败：输出文件为空"
                } else if (!copyAudioTransformFileToUri(context, tempFile, outputUri)) {
                    false to "保存失败：无法写入目标文件"
                } else {
                    true to "已保存为 ${outputName.ifBlank { audioTransformSuggestedFileName(inputName, mode) }}"
                }
            }.getOrElse {
                false to "转换异常：${it.message ?: "未知错误"}"
            }
            if (tempFile.exists()) {
                runCatching { tempFile.delete() }
            }
            handler.post {
                running = false
                updateResult(result.second, toast = true)
            }
        }, "Hchat-AudioTransform-Save").start()
    }

    fun launchSavePicker() {
        if (running) return
        val sourcePath = inputPath.takeIf { it.isNotBlank() && File(it).isFile }
        if (sourcePath == null) {
            updateResult("请先选择输入文件", toast = true)
            return
        }
        val activity = context as? Activity
        if (activity == null) {
            updateResult("当前页面无法打开保存选择器", toast = true)
            return
        }
        AudioTransformDocumentBridge.launchOutput(
            activity = activity,
            suggestedFileName = audioTransformSuggestedFileName(inputName, mode),
            mimeType = audioTransformOutputMime(mode)
        ) { uri, name ->
            startSave(uri, name)
        }
    }

    SettingsRouteTransition(
        targetState = route,
        label = "AudioTransformRoute",
        depthOf = { it.depth() }
    ) { currentRoute ->
        when (currentRoute) {
            AudioTransformRoute.Main -> {
                AudioTransformMainPage(
                    provider = provider,
                    mode = mode,
                    inputPath = inputPath,
                    inputName = inputName,
                    talker = selectedTalker,
                    splitEnabled = splitEnabled,
                    splitDurationSeconds = splitDurationSeconds,
                    clipStartMillis = clipStartMillis,
                    clipEndMillis = clipEndMillis,
                    running = running,
                    resultText = resultText,
                    listState = mainListState,
                    onBack = onBack,
                    onModeChange = { next ->
                        mode = next
                        sp.edit().putInt(AudioTransformSettings.KEY_MODE, next).apply()
                    },
                    onPickInput = {
                        val activity = context as? Activity
                        if (activity == null) {
                            updateResult("当前页面无法打开文件选择器", toast = true)
                        } else {
                            AudioTransformDocumentBridge.launchInput(activity) { path, name ->
                                inputPath = path
                                inputName = name
                                clipStartMillis = 0L
                                clipEndMillis = 0L
                                resultText = ""
                            }
                        }
                    },
                    onPickTalker = { route = AudioTransformRoute.ContactPicker },
                    onSplitEnabledChange = { enabled ->
                        splitEnabled = enabled
                        sp.edit().putBoolean(AudioTransformSettings.KEY_SPLIT_ENABLED, enabled).apply()
                    },
                    onSplitDurationChange = { next ->
                        splitDurationSeconds = next
                        next.toLongOrNull()
                            ?.takeIf { it >= AudioTransformSettings.MIN_SPLIT_DURATION_SECONDS }
                            ?.let { duration ->
                                sp.edit().putLong(
                                    AudioTransformSettings.KEY_SPLIT_DURATION_SECONDS,
                                    duration
                                ).apply()
                            }
                    },
                    onClipRangeChange = { source, start, end ->
                        if (inputPath == source) {
                            clipStartMillis = start.coerceAtLeast(0L)
                            clipEndMillis = end.coerceAtLeast(0L)
                        }
                    },
                    onClearInput = {
                        inputPath = ""
                        inputName = ""
                        clipStartMillis = 0L
                        clipEndMillis = 0L
                    },
                    onCancel = {
                        splitCancellation.set(true)
                        splitWorker.get()?.interrupt()
                        resultText = "正在取消"
                    },
                    onStart = {
                        if (mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) {
                            startSend()
                        } else {
                            launchSavePicker()
                        }
                    }
                )
            }
            AudioTransformRoute.ContactPicker -> {
                ContactPickerPage(
                    context = context,
                    request = ContactPickerRequest(
                        title = "选择发送聊天",
                        mode = ContactPickerMode.ALL_CHATS,
                        multiSelect = false,
                        existingValue = formatIds(listOfNotNull(selectedTalker.takeIf { it.isNotBlank() })),
                        onValue = {}
                    ),
                    onBack = { route = AudioTransformRoute.Main },
                    onConfirm = { selected ->
                        selectedTalker = selected.firstOrNull()?.id.orEmpty()
                        sp.edit().putString(AudioTransformSettings.KEY_LAST_TALKER, selectedTalker).apply()
                        route = AudioTransformRoute.Main
                    }
                )
            }
        }
    }
}

@Composable
private fun AudioTransformMainPage(
    provider: FeatureSettingsProvider,
    mode: Int,
    inputPath: String,
    inputName: String,
    talker: String,
    splitEnabled: Boolean,
    splitDurationSeconds: String,
    clipStartMillis: Long,
    clipEndMillis: Long,
    running: Boolean,
    resultText: String,
    listState: LazyListState,
    onBack: () -> Unit,
    onModeChange: (Int) -> Unit,
    onPickInput: () -> Unit,
    onPickTalker: () -> Unit,
    onSplitEnabledChange: (Boolean) -> Unit,
    onSplitDurationChange: (String) -> Unit,
    onClipRangeChange: (String, Long, Long) -> Unit,
    onClearInput: () -> Unit,
    onCancel: () -> Unit,
    onStart: () -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    val actionText = if (mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) "开始发送" else "开始转换"
    val cancellable = running && mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND && splitEnabled
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = actionText,
                onPrimaryClick = onStart,
                secondaryText = if (cancellable) "取消" else "返回",
                onSecondaryClick = if (cancellable) onCancel else onBack
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
            item { SmallTitle(text = "转换方式") }
            item {
                SettingsCard {
                    PopupChoiceRow(
                        title = "操作",
                        summary = audioTransformModeSummary(mode),
                        options = audioTransformModeOptions(),
                        currentValue = mode.toString(),
                        onValueChanged = { next -> onModeChange(next.toIntOrNull() ?: mode) },
                        enabled = !running
                    )
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "文件") }
            item {
                SettingsCard {
                    ActionRow("选择输入文件", audioTransformInputSummary(inputPath, inputName)) {
                        if (!running) onPickInput()
                    }
                    if (inputPath.isNotBlank()) {
                        InsetDivider()
                        AudioTransformPreviewEditor(
                            inputPath = inputPath,
                            inputName = inputName,
                            clipEnabled = mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND &&
                                splitEnabled,
                            clipStartMillis = clipStartMillis,
                            clipEndMillis = clipEndMillis,
                            enabled = !running,
                            onRangeChange = onClipRangeChange
                        )
                        InsetDivider()
                        ActionRow("清空输入文件", "移除当前已选文件") {
                            if (!running) onClearInput()
                        }
                    }
                    if (mode != AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) {
                        InsetDivider()
                        InfoRow("输出文件", audioTransformSuggestedFileName(inputName, mode))
                    }
                }
            }
            if (mode == AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "发送") }
                item {
                    SettingsCard {
                        ActionRow("选择聊天", audioTransformTalkerSummary(talker)) {
                            if (!running) onPickTalker()
                        }
                        InsetDivider()
                        SwitchRow(
                            checked = splitEnabled,
                            title = "裁剪与自动分割",
                            summary = "按选定起止区间裁剪，并按时长依次发送",
                            enabled = !running,
                            onCheckedChange = onSplitEnabledChange
                        )
                        if (splitEnabled) {
                            InsetDivider()
                            NumberInputRow(
                                title = "自动分割（秒）",
                                summary = "单位秒，最少 1 秒",
                                value = splitDurationSeconds,
                                onValueChange = onSplitDurationChange
                            )
                        }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "状态") }
            item {
                SettingsCard {
                    InfoRow("当前状态", if (running) "处理中" else "待开始")
                    if (resultText.isNotBlank()) {
                        InsetDivider()
                        InfoRow("最近结果", resultText)
                    }
                }
            }
        }
    }
}

@Composable
private fun AudioTransformPreviewEditor(
    inputPath: String,
    inputName: String,
    clipEnabled: Boolean,
    clipStartMillis: Long,
    clipEndMillis: Long,
    enabled: Boolean,
    onRangeChange: (String, Long, Long) -> Unit
) {
    val context = LocalContext.current
    val currentRangeChange by rememberUpdatedState(onRangeChange)
    val currentClipStart by rememberUpdatedState(clipStartMillis)
    val currentClipEnd by rememberUpdatedState(clipEndMillis)
    val currentClipEnabled by rememberUpdatedState(clipEnabled)
    var playablePath by remember(inputPath) { mutableStateOf("") }
    var previewPreparing by remember(inputPath) { mutableStateOf(true) }
    var previewError by remember(inputPath) { mutableStateOf("") }
    var player by remember(inputPath) { mutableStateOf<MediaPlayer?>(null) }
    var prepared by remember(inputPath) { mutableStateOf(false) }
    var playing by remember(inputPath) { mutableStateOf(false) }
    var durationMillis by remember(inputPath) { mutableStateOf(0) }
    var positionMillis by remember(inputPath) { mutableStateOf(0) }
    var playerError by remember(inputPath) { mutableStateOf("") }

    LaunchedEffect(inputPath, enabled) {
        playablePath = ""
        previewError = ""
        playerError = ""
        if (!enabled) {
            previewPreparing = false
            return@LaunchedEffect
        }
        previewPreparing = true
        var generatedPreview: File? = null
        try {
            val resolved = withContext(Dispatchers.IO) {
                val source = File(inputPath)
                check(source.isFile) { "输入文件不存在" }
                val previewBridge = AudioTransformBridge()
                if (previewBridge.getFileType(inputPath) != 1) {
                    inputPath
                } else {
                    val previewDir = File(context.cacheDir, "audio_transform_preview").apply { mkdirs() }
                    val output = File(
                        previewDir,
                        "preview_${System.currentTimeMillis()}_${System.nanoTime()}.m4a"
                    )
                    generatedPreview = output
                    val code = previewBridge.silkToM4a(
                        inputPath,
                        output.absolutePath,
                        AudioTransformBridge.DEFAULT_HZ
                    )
                    check(code == 0 && output.isFile && output.length() > 0L) {
                        "试听准备失败：${previewBridge.getErrorMessage(code)}"
                    }
                    output.absolutePath
                }
            }
            playablePath = resolved
            previewPreparing = false
            awaitCancellation()
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (throwable: Throwable) {
            previewPreparing = false
            previewError = throwable.message ?: "试听准备失败"
        } finally {
            generatedPreview?.let { runCatching { it.delete() } }
        }
    }

    DisposableEffect(playablePath, enabled) {
        val path = playablePath
        var effectPlayer: MediaPlayer? = null
        prepared = false
        playing = false
        if (path.isNotBlank() && enabled) {
            runCatching {
                MediaPlayer().also { media ->
                    effectPlayer = media
                    media.setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    media.setDataSource(path)
                    media.setOnPreparedListener { preparedPlayer ->
                        if (player !== preparedPlayer) return@setOnPreparedListener
                        val duration = preparedPlayer.duration.coerceAtLeast(0)
                        durationMillis = duration
                        prepared = duration > 0
                        playing = false
                        val maxStart = (duration - 1).coerceAtLeast(0).toLong()
                        val start = currentClipStart.coerceIn(0L, maxStart)
                        val end = currentClipEnd
                            .takeIf { it > start }
                            ?.coerceAtMost(duration.toLong())
                            ?: duration.toLong()
                        positionMillis = start.toInt()
                        runCatching { preparedPlayer.seekTo(positionMillis) }
                        currentRangeChange(inputPath, start, end)
                    }
                    media.setOnCompletionListener { completed ->
                        if (player !== completed) return@setOnCompletionListener
                        playing = false
                        val reset = if (currentClipEnabled) {
                            currentClipStart.coerceIn(
                                0L,
                                completed.duration.coerceAtLeast(0).toLong()
                            ).toInt()
                        } else {
                            0
                        }
                        runCatching { completed.seekTo(reset) }
                        positionMillis = reset
                    }
                    media.setOnErrorListener { failed, _, _ ->
                        if (player !== failed) return@setOnErrorListener true
                        prepared = false
                        playing = false
                        playerError = "当前音频无法试听"
                        true
                    }
                    player = media
                    media.prepareAsync()
                }
            }.onFailure {
                val failedPlayer = effectPlayer
                effectPlayer = null
                if (player === failedPlayer) player = null
                failedPlayer?.let { media ->
                    media.setOnPreparedListener(null)
                    media.setOnCompletionListener(null)
                    media.setOnErrorListener(null)
                    runCatching { media.release() }
                }
                prepared = false
                playing = false
                playerError = "当前音频无法试听"
            }
        }
        onDispose {
            val disposingPlayer = effectPlayer
            effectPlayer = null
            disposingPlayer?.let { media ->
                media.setOnPreparedListener(null)
                media.setOnCompletionListener(null)
                media.setOnErrorListener(null)
                runCatching { if (media.isPlaying) media.stop() }
                runCatching { media.release() }
            }
            if (player === disposingPlayer) player = null
            prepared = false
            playing = false
        }
    }

    LaunchedEffect(enabled, player, playing, prepared, clipEnabled, clipStartMillis, clipEndMillis) {
        val media = player
        if (!enabled && media != null && playing) {
            runCatching { media.pause() }
            playing = false
        }
        while (enabled && prepared && playing && media != null) {
            val current = runCatching { media.currentPosition.coerceAtLeast(0) }
                .getOrDefault(positionMillis)
            val end = if (clipEnabled) {
                clipEndMillis.takeIf { it > clipStartMillis }
                    ?.coerceAtMost(durationMillis.toLong())
                    ?.toInt()
                    ?: durationMillis
            } else {
                durationMillis
            }
            if (end > 0 && current >= end) {
                runCatching { media.pause() }
                val reset = if (clipEnabled) {
                    clipStartMillis.coerceIn(0L, end.toLong()).toInt()
                } else {
                    0
                }
                runCatching { media.seekTo(reset) }
                positionMillis = reset
                playing = false
                break
            }
            positionMillis = current
            delay(80L)
        }
    }

    fun seekTo(target: Int) {
        val media = player ?: return
        if (!prepared || durationMillis <= 0) return
        val maxStart = (durationMillis - 1).coerceAtLeast(0).toLong()
        val rangeStart = clipStartMillis.coerceIn(0L, maxStart).toInt()
        val rangeEnd = clipEndMillis
            .takeIf { it > rangeStart }
            ?.coerceAtMost(durationMillis.toLong())
            ?.toInt()
            ?: durationMillis
        val lowerBound = if (playing && clipEnabled) rangeStart else 0
        val upperBound = if (playing && clipEnabled) rangeEnd else durationMillis
        val next = target.coerceIn(lowerBound, upperBound.coerceAtLeast(lowerBound))
        runCatching { media.seekTo(next) }
        positionMillis = next
    }

    val effectiveClipStart = clipStartMillis.coerceIn(0L, durationMillis.coerceAtLeast(0).toLong())
    val effectiveClipEnd = clipEndMillis
        .takeIf { it > effectiveClipStart }
        ?.coerceAtMost(durationMillis.toLong())
        ?: durationMillis.toLong()
    val statusText = when {
        previewPreparing -> "正在准备试听"
        previewError.isNotBlank() -> previewError
        playerError.isNotBlank() -> playerError
        !enabled -> "处理中"
        !prepared -> "正在加载"
        playing -> "播放中"
        else -> "可试听"
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp)) {
            Text(
                text = inputName.ifBlank { File(inputPath).name.ifBlank { "已选音频" } },
                color = MiuixTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val playEnabled = enabled && prepared
                Box(
                    modifier = Modifier.size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (playEnabled) {
                                MiuixTheme.colorScheme.primary
                            } else {
                                MiuixTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                            }
                        )
                        .clickable(enabled = playEnabled) {
                            val media = player ?: return@clickable
                            if (playing) {
                                runCatching { media.pause() }
                                playing = false
                            } else {
                                val start = if (clipEnabled) {
                                    effectiveClipStart.toInt()
                                } else {
                                    0
                                }
                                val end = if (clipEnabled) {
                                    effectiveClipEnd.toInt()
                                } else {
                                    durationMillis
                                }
                                if (positionMillis < start || positionMillis >= end) {
                                    seekTo(start)
                                }
                                runCatching {
                                    media.start()
                                    playing = true
                                }.onFailure {
                                    playerError = "当前音频无法试听"
                                    playing = false
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = if (playing) NavIcons.Pause else NavIcons.Play,
                        contentDescription = if (playing) "暂停" else "播放",
                        colorFilter = ColorFilter.tint(
                            if (playEnabled) Color.White else MiuixTheme.colorScheme.onSurfaceVariantSummary
                        ),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
                    Text(
                        text = "${formatAudioTransformTime(positionMillis.toLong())} / " +
                            formatAudioTransformTime(durationMillis.toLong()),
                        color = MiuixTheme.colorScheme.onSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = statusText,
                        color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Slider(
                value = positionMillis.coerceIn(0, durationMillis.coerceAtLeast(0)).toFloat(),
                onValueChange = { next ->
                    if (enabled && prepared) seekTo(next.toInt())
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                    .graphicsLayer(alpha = if (enabled && prepared) 1f else 0.45f),
                valueRange = 0f..durationMillis.coerceAtLeast(1).toFloat(),
                steps = 0,
                showKeyPoints = false
            )
            if (clipEnabled && durationMillis > 0) {
                Text(
                    text = "选定区间  ${formatAudioTransformTime(effectiveClipStart)} - " +
                        formatAudioTransformTime(effectiveClipEnd),
                    color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        if (clipEnabled) {
            InsetDivider()
            ActionRow(
                title = "开始时间",
                summary = if (prepared) {
                    formatAudioTransformTime(effectiveClipStart)
                } else {
                    "等待音频加载"
                }
            ) {
                if (!enabled || !prepared) return@ActionRow
                if (positionMillis.toLong() >= effectiveClipEnd) {
                    Toast.makeText(context, "开始时间必须早于结束时间", Toast.LENGTH_SHORT).show()
                } else {
                    currentRangeChange(inputPath, positionMillis.toLong(), effectiveClipEnd)
                }
            }
            InsetDivider()
            ActionRow(
                title = "结束时间",
                summary = if (prepared) {
                    formatAudioTransformTime(effectiveClipEnd)
                } else {
                    "等待音频加载"
                }
            ) {
                if (!enabled || !prepared) return@ActionRow
                if (positionMillis.toLong() <= effectiveClipStart) {
                    Toast.makeText(context, "结束时间必须晚于开始时间", Toast.LENGTH_SHORT).show()
                } else {
                    currentRangeChange(inputPath, effectiveClipStart, positionMillis.toLong())
                }
            }
            InsetDivider()
            ActionRow("重新设置", "恢复完整音频区间") {
                if (!enabled || !prepared) return@ActionRow
                currentRangeChange(inputPath, 0L, durationMillis.toLong())
                seekTo(0)
            }
        }
    }
}

private fun formatAudioTransformTime(millis: Long): String {
    val totalSeconds = millis.coerceAtLeast(0L) / 1000L
    val seconds = totalSeconds % 60L
    val minutes = totalSeconds / 60L % 60L
    val hours = totalSeconds / 3600L
    return if (hours > 0L) {
        String.format(Locale.ROOT, "%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
    }
}

private fun audioTransformModeOptions(): List<PopupChoice<String>> = listOf(
    PopupChoice("任意音频转silk保存", AudioTransformSettings.MODE_AUDIO_TO_SILK_SAVE.toString()),
    PopupChoice("任意音频转silk发送", AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND.toString()),
    PopupChoice("Silk 转 MP3 保存", AudioTransformSettings.MODE_SILK_TO_MP3_SAVE.toString()),
    PopupChoice("Silk 转 M4A 保存", AudioTransformSettings.MODE_SILK_TO_M4A_SAVE.toString())
)

private fun audioTransformModeSummary(mode: Int): String = when (mode) {
    AudioTransformSettings.MODE_AUDIO_TO_SILK_SEND -> "把任意音频转成微信语音并直接发送"
    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> "把 Silk 语音导出为 MP3 文件"
    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> "把 Silk 语音导出为 M4A 文件"
    else -> "把任意音频转成 Silk 并保存到本地"
}

private fun audioTransformValidateInput(
    mode: Int,
    inputPath: String,
    bridge: AudioTransformBridge
): String? {
    if (!File(inputPath).isFile) return "输入文件不存在"
    if (mode == AudioTransformSettings.MODE_SILK_TO_MP3_SAVE ||
        mode == AudioTransformSettings.MODE_SILK_TO_M4A_SAVE
    ) {
        val fileType = bridge.getFileType(inputPath)
        if (fileType != 1) return "当前模式只支持 Silk 输入"
    }
    return null
}

private fun audioTransformSuggestedFileName(inputName: String, mode: Int): String {
    val rawName = inputName.trim().ifBlank { "audio_output" }
    val base = rawName.substringBeforeLast('.').ifBlank { rawName }
    return base + audioTransformOutputExtension(mode)
}

private fun audioTransformOutputExtension(mode: Int): String = when (mode) {
    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> ".mp3"
    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> ".m4a"
    else -> ".silk"
}

private fun audioTransformOutputMime(mode: Int): String = when (mode) {
    AudioTransformSettings.MODE_SILK_TO_MP3_SAVE -> "audio/mpeg"
    AudioTransformSettings.MODE_SILK_TO_M4A_SAVE -> "audio/mp4"
    else -> "application/octet-stream"
}

private fun audioTransformInputSummary(inputPath: String, inputName: String): String {
    if (inputPath.isBlank()) return "未选择"
    return inputName.ifBlank { File(inputPath).name.ifBlank { "已选择文件" } }
}

private fun audioTransformTalkerSummary(talker: String): String {
    if (talker.isBlank()) return "未选择"
    val display = runCatching { WeChatApis.contacts()?.getDisplayName(talker) }.getOrNull().orEmpty()
    return if (display.isBlank() || display == talker) talker else "$display · $talker"
}

private fun copyAudioTransformFileToUri(context: Context, source: File, uri: Uri): Boolean {
    return runCatching {
        context.contentResolver.openOutputStream(uri, "w")?.use { output ->
            source.inputStream().use { input -> input.copyTo(output) }
        } != null
    }.getOrDefault(false)
}
}

@Composable
internal fun FeatureListCard(
    providers: List<FeatureSettingsProvider>,
    emptyText: String,
    onOpenProvider: (FeatureSettingsProvider) -> Unit
) {
    SettingsCard {
        if (providers.isEmpty()) {
            EmptyText(emptyText)
        } else {
            providers.forEachIndexed { index, provider ->
                FeatureRow(
                    provider = provider,
                    onClick = { onOpenProvider(provider) }
                )
                if (index < providers.lastIndex) {
                    InsetDivider()
                }
            }
        }
    }
}

@Composable
internal fun FeatureGroupRow(group: FeatureGroupEntry, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(
                onClick = onClick,
                onPressedChange = { pressed = it }
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = group.title, color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(
                text = "${group.providers.size} 项功能",
                color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
                fontSize = 12.sp
            )
        }
        Text(text = "›", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 22.sp)
    }
}

@Composable
internal fun FeatureRow(provider: FeatureSettingsProvider, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val pressFeedbackColor = rememberPressFeedbackColor(pressed)
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(pressFeedbackColor)
            .responsiveTap(
                onClick = onClick,
                onPressedChange = { pressed = it }
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = provider.title(), color = MiuixTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
            Text(text = provider.subtitle(), color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 12.sp)
        }
        Text(text = "›", color = MiuixTheme.colorScheme.onSurfaceVariantSummary, fontSize = 22.sp)
    }
}

