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
internal fun TransferBindingListPage(
    bindings: List<TransferRuleBinding>,
    templates: List<TransferRuleTemplate>,
    onBack: () -> Unit,
    onOpen: (Int, TransferRuleBinding) -> Unit,
    onAdd: () -> Unit,
    onDeleteBindings: (List<TransferRuleBinding>) -> Unit
) {
    val context = LocalContext.current
    var category by remember { mutableStateOf(ConversationRuleCategory.ALL) }
    var query by remember { mutableStateOf("") }
    var batchDeleteMode by remember { mutableStateOf(false) }
    var selectedIds by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val lower = query.trim().lowercase(Locale.US)
    val visible = bindings.mapIndexed { index, value -> index to value }.filter { (_, value) ->
        conversationRuleCategoryMatches(value, category) && (
            lower.isBlank() || value.label.lowercase(Locale.US).contains(lower) ||
                value.targetId.lowercase(Locale.US).contains(lower) ||
                templates.firstOrNull { it.id == value.templateId }?.name?.lowercase(Locale.US)?.contains(lower) == true
            )
    }
    val visibleIds = visible.mapTo(LinkedHashSet()) { it.second.id }
    val allVisibleSelected = visibleIds.isNotEmpty() && visibleIds.all { it in selectedIds }
    val selectedBindings = bindings.filter { it.id in selectedIds }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "适用聊天",
        largeTitle = "适用聊天",
        scrollBehavior = scrollBehavior,
        bottomBar = {
            if (batchDeleteMode) {
                BottomActionBar(
                    primaryText = "删除所选（${selectedBindings.size}）",
                    onPrimaryClick = {
                        if (selectedBindings.isEmpty()) {
                            Toast.makeText(context, "请先选择适用聊天", Toast.LENGTH_SHORT).show()
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
                    primaryText = "添加聊天",
                    onPrimaryClick = onAdd,
                    secondaryText = "返回",
                    onSecondaryClick = onBack,
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
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item {
                SettingsCard {
                    ConversationRuleCategoryTabs(
                        selected = category,
                        onSelected = { category = it },
                        includeOfficial = false
                    )
                }
            }
            item { SettingsCard { InputRow("搜索聊天", "昵称 / ID / 模板名", query) { query = it } } }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "聊天 · ${visible.size}/${bindings.size} 项") }
            item {
                SettingsCard {
                    if (visible.isEmpty()) {
                        EmptyText(if (bindings.isEmpty()) "暂无适用聊天。" else "没有匹配结果。")
                    } else {
                        visible.forEachIndexed { row, (index, binding) ->
                            if (batchDeleteMode) {
                                OptionChoiceRow(
                                    item = OptionItem(
                                        label = binding.label.ifBlank { binding.targetId },
                                        value = index,
                                        summary = describeTransferBinding(binding, templates)
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
                                    binding.label.ifBlank { binding.targetId },
                                    describeTransferBinding(binding, templates)
                                ) { onOpen(index, binding) }
                            }
                            if (row < visible.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
    BatchDeleteConfirmDialog(
        show = showDeleteConfirm,
        message = "将删除已选的 ${selectedBindings.size} 个适用聊天，此操作不可撤销。",
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
internal fun TransferBindingEditorPage(
    context: Context,
    request: TransferBindingEditorRequest,
    templates: List<TransferRuleTemplate>,
    onBack: () -> Unit,
    onSave: (TransferRuleBinding) -> Unit,
    onDelete: () -> Unit
) {
    var enabled by remember(request) { mutableStateOf(request.binding.enabled) }
    var templateId by remember(request) { mutableStateOf(request.binding.templateId) }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = request.binding.label.ifBlank { request.binding.targetId },
        largeTitle = request.binding.label.ifBlank { request.binding.targetId },
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                "保存聊天",
                {
                    onSave(request.binding.copy(enabled = enabled, templateId = templateId))
                    Toast.makeText(context, "适用聊天已保存", Toast.LENGTH_SHORT).show()
                },
                "返回",
                onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "聊天") }
            item {
                SettingsCard {
                    InfoRow("ID", request.binding.targetId)
                    InsetDivider()
                    SwitchRow(enabled, "启用自动收款", "关闭后该聊天不会自动收款") { enabled = it }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "模板") }
            item {
                SettingsCard {
                    OptionChoiceRow(
                        OptionItem("跟随默认规则", -1, "使用默认模板或全局设置"),
                        templateId.isBlank()
                    ) { templateId = "" }
                    templates.forEachIndexed { index, template ->
                        InsetDivider()
                        OptionChoiceRow(
                            OptionItem(template.name.ifBlank { "模板 ${index + 1}" }, index, describeTransferTemplate(template)),
                            templateId == template.id
                        ) { templateId = template.id }
                    }
                }
            }
            if (request.canDelete) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item { SettingsCard { ActionRow("移除适用聊天", "移除后恢复默认规则", onDelete) } }
            }
        }
    }
}

@Composable
internal fun TransferBatchApplyPage(
    templates: List<TransferRuleTemplate>,
    bindings: List<TransferRuleBinding>,
    onBack: () -> Unit,
    onPickChats: (String) -> Unit
) {
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = "批量套用模板",
        largeTitle = "批量套用模板",
        scrollBehavior = scrollBehavior,
        bottomBar = { BottomActionBar("返回", onBack) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "选择模板") }
            item {
                SettingsCard {
                    if (templates.isEmpty()) {
                        EmptyText("请先新增收款规则模板。")
                    } else {
                        templates.forEachIndexed { index, template ->
                            val count = bindings.count { it.templateId == template.id }
                            SelectRow(template.name, "$count 个聊天 · ${describeTransferTemplate(template)}") {
                                onPickChats(template.id)
                            }
                            if (index < templates.lastIndex) InsetDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun TransferTemplateEditorPage(
    context: Context,
    request: TransferTemplateEditorRequest,
    onBack: () -> Unit,
    onSave: (TransferRuleTemplate) -> Unit,
    onDelete: () -> Unit
) {
    var value by remember(request) { mutableStateOf(request.template) }
    var showReplySteps by remember { mutableStateOf(false) }
    var replyTarget by remember { mutableStateOf(TransferReplyTarget.PRIVATE) }
    var groupReplySteps by remember(request) {
        mutableStateOf(request.template.groupReplySteps ?: request.template.replySteps)
    }
    var picker by remember { mutableStateOf<ContactPickerRequest?>(null) }
    val accountOptions = remember { transferReceiveAccountOptions(context) }
    if (showReplySteps) {
        RedPacketReplyStepsPage(
            context = context,
            title = if (replyTarget == TransferReplyTarget.GROUP) "模板群聊收款回复" else "模板私聊收款回复",
            initialSteps = if (replyTarget == TransferReplyTarget.GROUP) groupReplySteps else value.replySteps,
            templateVariables = transferTemplateVariables,
            onBack = { showReplySteps = false },
            onSave = {
                if (replyTarget == TransferReplyTarget.GROUP) groupReplySteps = it else value = value.copy(replySteps = it)
                showReplySteps = false
            }
        )
        return
    }
    picker?.let { requestPicker ->
        ContactPickerPage(
            context = context,
            request = requestPicker,
            onBack = { picker = null },
            onConfirm = {
                requestPicker.onValue(formatIds(it.map { option -> option.id }))
                picker = null
            }
        )
        return
    }
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = value.name.ifBlank { "收款模板" },
        largeTitle = value.name.ifBlank { "收款模板" },
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                "保存模板",
                {
                    onSave(value.copy(
                        name = value.name.ifBlank { "收款模板" },
                        replySteps = cleanRedPacketReplySteps(value.replySteps),
                        groupReplySteps = cleanRedPacketReplySteps(groupReplySteps)
                    ))
                    Toast.makeText(context, "收款模板已保存", Toast.LENGTH_SHORT).show()
                },
                "返回",
                onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 84.dp
            )
        ) {
            item { SmallTitle(text = "模板") }
            item {
                SettingsCard {
                    InputRow("模板名称", "用于默认规则和聊天绑定", value.name) { value = value.copy(name = it) }
                    InsetDivider()
                    SwitchRow(value.enabled, "启用模板", "关闭后使用该模板的聊天不会自动收款") { value = value.copy(enabled = it) }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "收款") }
            item {
                SettingsCard {
                    PopupChoiceRow("收款位置", "选择转账到账账户", accountOptions, value.receiveAccount, onValueChanged = { value = value.copy(receiveAccount = it) })
                    InsetDivider()
                    SwitchRow(value.refundRejected, "拒收时退回", "规则不通过时原路退回") { value = value.copy(refundRejected = it) }
                    InsetDivider()
                    PopupOptionRow("收款延迟", transferDelayModeLabel(value.delayMode), redPacketDelayModeOptions(true), value.delayMode, onValueChanged = { value = value.copy(delayMode = it) })
                    if (value.delayMode == TransferRuleConfig.DELAY_CUSTOM) {
                        InsetDivider(); NumberInputRow("自定义延迟", "单位 ms", value.delayMs.toString()) { value = value.copy(delayMs = it.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L) }
                    } else if (value.delayMode == TransferRuleConfig.DELAY_RANDOM) {
                        InsetDivider(); NumberInputRow("最小延迟", "单位 ms", value.randomMinMs.toString()) { value = value.copy(randomMinMs = it.toLongOrNull()?.coerceIn(0L, 600000L) ?: 0L) }
                        InsetDivider(); NumberInputRow("最大延迟", "单位 ms", value.randomMaxMs.toString()) { value = value.copy(randomMaxMs = it.toLongOrNull()?.coerceIn(value.randomMinMs, 600000L) ?: value.randomMinMs) }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "过滤") }
            item {
                SettingsCard {
                    PopupOptionRow("收款范围", transferListModeLabel(value.listMode), optionItems("全部接收" to 0, "只接收白名单" to 1, "拒收黑名单" to 2), value.listMode, onValueChanged = { value = value.copy(listMode = it) })
                    if (value.listMode == 1 || value.listMode == 2) {
                        InsetDivider()
                        val listValue = if (value.listMode == 1) value.whitelist else value.blacklist
                        ActionRow(if (value.listMode == 1) "白名单" else "黑名单", autoReplySelectedIdSummary(listValue)) {
                            picker = ContactPickerRequest(
                                if (value.listMode == 1) "选择白名单" else "选择黑名单",
                                ContactPickerMode.BOTH,
                                true,
                                listValue,
                                { selected -> value = if (value.listMode == 1) value.copy(whitelist = selected) else value.copy(blacklist = selected) },
                                true
                            )
                        }
                    }
                    InsetDivider()
                    SwitchRow(value.amountEnabled, "启用金额规则", "按转账金额决定接收或拒收") { value = value.copy(amountEnabled = it) }
                    if (value.amountEnabled) {
                        InsetDivider(); PopupOptionRow("金额条件", transferAmountConditionLabel(value.amountCondition), optionItems("大于" to 0, "小于" to 1, "等于" to 2), value.amountCondition, onValueChanged = { value = value.copy(amountCondition = it) })
                        InsetDivider(); InputRow("金额数值", "单位元，例如 10.5", value.amountValue) { text -> value = value.copy(amountValue = text.filter { it.isDigit() || it == '.' }) }
                        InsetDivider(); PopupOptionRow("命中后动作", transferAmountActionLabel(value.amountAction), optionItems("拒收/忽略" to 0, "仅接收满足条件" to 1), value.amountAction, onValueChanged = { value = value.copy(amountAction = it) })
                    }
                    InsetDivider()
                    PopupOptionRow("关键词规则", transferKeywordModeLabel(value.keywordMode), optionItems("不启用" to 0, "必须包含关键词" to 1, "包含则拒收" to 2), value.keywordMode, onValueChanged = { value = value.copy(keywordMode = it) })
                    if (value.keywordMode != 0) {
                        InsetDivider(); InputRow("关键词", "多个关键词用 |、逗号或换行分隔", value.keywords, minLines = 2) { value = value.copy(keywords = it) }
                    }
                    InsetDivider()
                    SwitchRow(value.quietEnabled, "禁收时段", "指定时段内不自动收款") { value = value.copy(quietEnabled = it) }
                    if (value.quietEnabled) {
                        InsetDivider(); TimeOfDayPickerRow("开始时间", formatRedPacketSecond(value.quietStartSecond)) { value = value.copy(quietStartSecond = parseRedPacketSecond(it, value.quietStartSecond)) }
                        InsetDivider(); TimeOfDayPickerRow("结束时间", formatRedPacketSecond(value.quietEndSecond)) { value = value.copy(quietEndSecond = parseRedPacketSecond(it, value.quietEndSecond)) }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "收款后回复") }
            item {
                SettingsCard {
                    SelectRow("私聊收款回复", describeRedPacketReplySteps(value.replySteps)) {
                        replyTarget = TransferReplyTarget.PRIVATE
                        showReplySteps = true
                    }
                    InsetDivider()
                    SelectRow("群聊收款回复", describeRedPacketReplySteps(groupReplySteps)) {
                        replyTarget = TransferReplyTarget.GROUP
                        showReplySteps = true
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "成功提醒") }
            item {
                TransferNotificationSettingsCard(
                    context,
                    value.notifySystemEnabled, { value = value.copy(notifySystemEnabled = it) },
                    value.notifyToastEnabled, { value = value.copy(notifyToastEnabled = it) },
                    value.notifySoundEnabled, { value = value.copy(notifySoundEnabled = it) },
                    value.notifySoundMode, { value = value.copy(notifySoundMode = it, notifySoundUri = "") },
                    value.notifyVibrateEnabled, { value = value.copy(notifyVibrateEnabled = it) },
                    value.notifySoundUri, { value = value.copy(notifySoundUri = it) },
                    value.notifyText, { value = value.copy(notifyText = it) },
                    value.notifyToastText, { value = value.copy(notifyToastText = it) },
                    value.announceEnabled, { value = value.copy(announceEnabled = it) },
                    value.announceText, { value = value.copy(announceText = it) }
                )
            }
            if (request.canDelete) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "操作") }
                item { SettingsCard { ActionRow("删除模板", "删除后相关聊天恢复默认规则", onDelete) } }
            }
        }
    }
}

@Composable
internal fun TransferNotificationSettingsCard(
    context: Context,
    system: Boolean,
    onSystem: (Boolean) -> Unit,
    toast: Boolean,
    onToast: (Boolean) -> Unit,
    sound: Boolean,
    onSound: (Boolean) -> Unit,
    soundMode: Int,
    onSoundMode: (Int) -> Unit,
    vibrate: Boolean,
    onVibrate: (Boolean) -> Unit,
    soundUri: String,
    onSoundUri: (String) -> Unit,
    noticeText: String,
    onNoticeText: (String) -> Unit,
    toastText: String,
    onToastText: (String) -> Unit,
    announce: Boolean,
    onAnnounce: (Boolean) -> Unit,
    announceText: String,
    onAnnounceText: (String) -> Unit
) {
    SettingsCard {
        SwitchRow(system, "通知栏提醒", "收款请求成功后显示") { onSystem(it) }
        if (system) {
            InsetDivider(); VariableInputRow("通知栏文案", "支持下方变量", noticeText, transferTemplateVariables, onValueChange = onNoticeText)
        }
        InsetDivider()
        SwitchRow(toast, "浮窗提醒", "收款请求成功后短暂提示") { onToast(it) }
        if (toast) {
            InsetDivider(); VariableInputRow("浮窗文案", "支持下方变量", toastText, transferTemplateVariables, onValueChange = onToastText)
        }
        InsetDivider()
        SwitchRow(sound, "通知铃声", "开启后播放通知铃声") { onSound(it) }
        if (sound) {
            InsetDivider()
            PopupOptionRow(
                "铃声模式",
                if (soundMode == AutoTransferSettings.NOTIFY_SOUND_MODE_CUSTOM) "从文件选择铃声" else "选择系统铃声",
                optionItems("选择系统铃声" to AutoTransferSettings.NOTIFY_SOUND_MODE_SYSTEM, "从文件选择铃声" to AutoTransferSettings.NOTIFY_SOUND_MODE_CUSTOM),
                soundMode,
                onSoundMode
            )
            InsetDivider()
            ActionRow("选择铃声", ringtoneDisplayName(context, soundUri, soundMode)) {
                val activity = context as? Activity
                if (activity == null) {
                    Toast.makeText(context, "当前页面无法打开铃声选择器", Toast.LENGTH_SHORT).show()
                } else {
                    val picked: (String) -> Unit = { uri ->
                        if (uri.isNotBlank()) {
                            onSoundUri(
                                if (soundMode == AutoTransferSettings.NOTIFY_SOUND_MODE_CUSTOM) {
                                    CustomNotificationRuntime.freezeRingtoneUri(context, uri)
                                } else uri
                            )
                        }
                    }
                    if (soundMode == AutoTransferSettings.NOTIFY_SOUND_MODE_CUSTOM) {
                        RingtonePickerBridge.launchFile(activity, picked)
                    } else {
                        RingtonePickerBridge.launchSystem(activity, soundUri, picked)
                    }
                }
            }
        }
        InsetDivider()
        SwitchRow(vibrate, "通知震动", "开启后触发通知震动") { onVibrate(it) }
        InsetDivider()
        SwitchRow(announce, "收款语音播报", "收款请求成功后用系统语音播报") { onAnnounce(it) }
        if (announce) {
            InsetDivider(); VariableInputRow("播报文案", "支持下方变量", announceText, transferTemplateVariables, onValueChange = onAnnounceText)
        }
    }
}

@Composable
internal fun FakeWalletBalanceMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, FakeWalletBalanceSettings.PREFS_NAME) }
    var balanceEnabled by remember {
        mutableStateOf(
            FakeWalletBalanceSettings.isAccountEnabled(
                sp,
                FakeWalletBalanceSettings.KEY_BALANCE_ENABLE
            )
        )
    }
    var lqtEnabled by remember {
        mutableStateOf(
            FakeWalletBalanceSettings.isAccountEnabled(
                sp,
                FakeWalletBalanceSettings.KEY_LQT_ENABLE
            )
        )
    }
    var businessEnabled by remember {
        mutableStateOf(
            FakeWalletBalanceSettings.isAccountEnabled(
                sp,
                FakeWalletBalanceSettings.KEY_BUSINESS_ENABLE
            )
        )
    }
    val storedBalanceAmount = remember {
        sp.getString(
            FakeWalletBalanceSettings.KEY_BALANCE_AMOUNT,
            FakeWalletBalanceSettings.DEFAULT_AMOUNT
        ) ?: FakeWalletBalanceSettings.DEFAULT_AMOUNT
    }
    val storedLqtAmount = remember {
        sp.getString(
            FakeWalletBalanceSettings.KEY_LQT_AMOUNT,
            FakeWalletBalanceSettings.DEFAULT_AMOUNT
        ) ?: FakeWalletBalanceSettings.DEFAULT_AMOUNT
    }
    val storedBusinessAmount = remember {
        sp.getString(FakeWalletBalanceSettings.KEY_BUSINESS_AMOUNT, null)
            ?: storedLqtAmount
    }
    var balanceAmount by remember {
        mutableStateOf(FakeWalletBalanceSettings.normalizeAmount(storedBalanceAmount))
    }
    var lqtAmount by remember {
        mutableStateOf(FakeWalletBalanceSettings.normalizeAmount(storedLqtAmount))
    }
    var businessAmount by remember {
        mutableStateOf(FakeWalletBalanceSettings.normalizeAmount(storedBusinessAmount))
    }
    var balanceMode by remember {
        mutableStateOf(
            FakeWalletBalanceSettings.amountMode(
                sp,
                FakeWalletBalanceSettings.KEY_BALANCE_MODE,
                storedBalanceAmount
            )
        )
    }
    var lqtMode by remember {
        mutableStateOf(
            FakeWalletBalanceSettings.amountMode(
                sp,
                FakeWalletBalanceSettings.KEY_LQT_MODE,
                storedLqtAmount
            )
        )
    }
    var businessMode by remember {
        val fallbackMode = if (!sp.contains(FakeWalletBalanceSettings.KEY_BUSINESS_AMOUNT)) {
            FakeWalletBalanceSettings.amountMode(
                sp,
                FakeWalletBalanceSettings.KEY_LQT_MODE,
                storedLqtAmount
            )
        } else {
            FakeWalletBalanceSettings.DEFAULT_MODE
        }
        mutableStateOf(
            FakeWalletBalanceSettings.amountMode(
                sp,
                FakeWalletBalanceSettings.KEY_BUSINESS_MODE,
                storedBusinessAmount,
                fallbackMode
            )
        )
    }
    val amountModeOptions = remember {
        listOf(
            PopupChoice("固定金额", FakeWalletBalanceSettings.MODE_FIXED),
            PopupChoice("增加金额", FakeWalletBalanceSettings.MODE_INCREASE),
            PopupChoice("减少金额", FakeWalletBalanceSettings.MODE_DECREASE)
        )
    }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()

    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存设置",
                onPrimaryClick = {
                    val normalizedBalance = FakeWalletBalanceSettings.normalizeAmount(balanceAmount)
                    val normalizedLqt = FakeWalletBalanceSettings.normalizeAmount(lqtAmount)
                    val normalizedBusiness = FakeWalletBalanceSettings.normalizeAmount(businessAmount)
                    balanceAmount = normalizedBalance
                    lqtAmount = normalizedLqt
                    businessAmount = normalizedBusiness
                    sp.edit()
                        .putString(FakeWalletBalanceSettings.KEY_BALANCE_AMOUNT, normalizedBalance)
                        .putString(FakeWalletBalanceSettings.KEY_LQT_AMOUNT, normalizedLqt)
                        .putString(FakeWalletBalanceSettings.KEY_BUSINESS_AMOUNT, normalizedBusiness)
                        .putString(FakeWalletBalanceSettings.KEY_BALANCE_MODE, balanceMode)
                        .putString(FakeWalletBalanceSettings.KEY_LQT_MODE, lqtMode)
                        .putString(FakeWalletBalanceSettings.KEY_BUSINESS_MODE, businessMode)
                        .apply()
                    Toast.makeText(context, "设置已保存", Toast.LENGTH_SHORT).show()
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
            item { SmallTitle(text = "显示") }
            item {
                SettingsCard {
                    SwitchRow(
                        checked = balanceEnabled,
                        title = "余额",
                        summary = "固定显示或按真实金额动态增减"
                    ) { enabled ->
                        balanceEnabled = enabled
                        sp.edit().putBoolean(FakeWalletBalanceSettings.KEY_BALANCE_ENABLE, enabled).apply()
                    }
                    InsetDivider()
                    SwitchRow(
                        checked = lqtEnabled,
                        title = "零钱通",
                        summary = "固定显示或按真实金额动态增减"
                    ) { enabled ->
                        lqtEnabled = enabled
                        sp.edit().putBoolean(FakeWalletBalanceSettings.KEY_LQT_ENABLE, enabled).apply()
                    }
                    InsetDivider()
                    SwitchRow(
                        checked = businessEnabled,
                        title = "经营账户",
                        summary = "固定显示或按真实金额动态增减"
                    ) { enabled ->
                        businessEnabled = enabled
                        sp.edit().putBoolean(FakeWalletBalanceSettings.KEY_BUSINESS_ENABLE, enabled).apply()
                    }
                }
            }
            if (balanceEnabled || lqtEnabled || businessEnabled) {
                item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "金额") }
                item {
                    SettingsCard {
                        if (balanceEnabled) {
                            PopupChoiceRow(
                                title = "余额显示方式",
                                summary = "选择固定显示或按真实余额计算",
                                options = amountModeOptions,
                                currentValue = balanceMode,
                                onValueChanged = { balanceMode = it }
                            )
                            InsetDivider()
                            InputRow(
                                when (balanceMode) {
                                    FakeWalletBalanceSettings.MODE_INCREASE -> "余额增加金额"
                                    FakeWalletBalanceSettings.MODE_DECREASE -> "余额减少金额"
                                    else -> "余额金额"
                                },
                                if (balanceMode == FakeWalletBalanceSettings.MODE_FIXED) {
                                    "直接显示该金额"
                                } else {
                                    "基于微信真实余额计算"
                                },
                                balanceAmount
                            ) { value ->
                                balanceAmount = value.filter { it.isDigit() || it == '.' || it == ',' }
                            }
                        }
                        if (balanceEnabled && (lqtEnabled || businessEnabled)) {
                            InsetDivider()
                        }
                        if (lqtEnabled) {
                            PopupChoiceRow(
                                title = "零钱通显示方式",
                                summary = "选择固定显示或按真实金额计算",
                                options = amountModeOptions,
                                currentValue = lqtMode,
                                onValueChanged = { lqtMode = it }
                            )
                            InsetDivider()
                            InputRow(
                                when (lqtMode) {
                                    FakeWalletBalanceSettings.MODE_INCREASE -> "零钱通增加金额"
                                    FakeWalletBalanceSettings.MODE_DECREASE -> "零钱通减少金额"
                                    else -> "零钱通金额"
                                },
                                if (lqtMode == FakeWalletBalanceSettings.MODE_FIXED) {
                                    "直接显示该金额"
                                } else {
                                    "基于微信真实金额计算"
                                },
                                lqtAmount
                            ) { value ->
                                lqtAmount = value.filter { it.isDigit() || it == '.' || it == ',' }
                            }
                        }
                        if (lqtEnabled && businessEnabled) {
                            InsetDivider()
                        }
                        if (businessEnabled) {
                            PopupChoiceRow(
                                title = "经营账户显示方式",
                                summary = "选择固定显示或按真实金额计算",
                                options = amountModeOptions,
                                currentValue = businessMode,
                                onValueChanged = { businessMode = it }
                            )
                            InsetDivider()
                            InputRow(
                                when (businessMode) {
                                    FakeWalletBalanceSettings.MODE_INCREASE -> "经营账户增加金额"
                                    FakeWalletBalanceSettings.MODE_DECREASE -> "经营账户减少金额"
                                    else -> "经营账户金额"
                                },
                                if (businessMode == FakeWalletBalanceSettings.MODE_FIXED) {
                                    "直接显示该金额"
                                } else {
                                    "基于微信真实金额计算"
                                },
                                businessAmount
                            ) { value ->
                                businessAmount = value.filter { it.isDigit() || it == '.' || it == ',' }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun MemberTitleMiuixPage(
    context: Context,
    provider: FeatureSettingsProvider,
    onBack: () -> Unit
) {
    val sp = remember { HchatStorage.preferences(context, MemberTitleSettings.PREFS_NAME) }
    var ownerTitle by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_OWNER_TITLE, MemberTitleSettings.DEFAULT_OWNER_TITLE) ?: MemberTitleSettings.DEFAULT_OWNER_TITLE) }
    var adminTitle by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_ADMIN_TITLE, MemberTitleSettings.DEFAULT_ADMIN_TITLE) ?: MemberTitleSettings.DEFAULT_ADMIN_TITLE) }
    var memberTitle by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_MEMBER_TITLE, MemberTitleSettings.DEFAULT_MEMBER_TITLE) ?: MemberTitleSettings.DEFAULT_MEMBER_TITLE) }
    var ownerColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_OWNER_COLOR, MemberTitleSettings.DEFAULT_OWNER_COLOR) ?: MemberTitleSettings.DEFAULT_OWNER_COLOR) }
    var adminColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_ADMIN_COLOR, MemberTitleSettings.DEFAULT_ADMIN_COLOR) ?: MemberTitleSettings.DEFAULT_ADMIN_COLOR) }
    var memberColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_MEMBER_COLOR, MemberTitleSettings.DEFAULT_MEMBER_COLOR) ?: MemberTitleSettings.DEFAULT_MEMBER_COLOR) }
    var customColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_CUSTOM_COLOR, MemberTitleSettings.DEFAULT_CUSTOM_COLOR) ?: MemberTitleSettings.DEFAULT_CUSTOM_COLOR) }
    var ownerTextColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_OWNER_TEXT_COLOR, MemberTitleSettings.DEFAULT_TEXT_COLOR) ?: MemberTitleSettings.DEFAULT_TEXT_COLOR) }
    var adminTextColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_ADMIN_TEXT_COLOR, MemberTitleSettings.DEFAULT_TEXT_COLOR) ?: MemberTitleSettings.DEFAULT_TEXT_COLOR) }
    var memberTextColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_MEMBER_TEXT_COLOR, MemberTitleSettings.DEFAULT_TEXT_COLOR) ?: MemberTitleSettings.DEFAULT_TEXT_COLOR) }
    var customTextColor by remember { mutableStateOf(sp.getString(MemberTitleSettings.KEY_CUSTOM_TEXT_COLOR, MemberTitleSettings.DEFAULT_TEXT_COLOR) ?: MemberTitleSettings.DEFAULT_TEXT_COLOR) }
    var showMember by remember { mutableStateOf(sp.getBoolean(MemberTitleSettings.KEY_SHOW_MEMBER, MemberTitleSettings.DEFAULT_SHOW_MEMBER)) }
    val listState = rememberLazyListState()
    val scrollBehavior = MiuixScrollBehavior()
    PageScaffold(
        title = provider.title(),
        largeTitle = provider.title(),
        scrollBehavior = scrollBehavior,
        bottomBar = {
            BottomActionBar(
                primaryText = "保存设置",
                onPrimaryClick = {
                    sp.edit()
                        .putString(MemberTitleSettings.KEY_OWNER_TITLE, MemberTitleStore.cleanTitle(ownerTitle).ifEmpty { MemberTitleSettings.DEFAULT_OWNER_TITLE })
                        .putString(MemberTitleSettings.KEY_ADMIN_TITLE, MemberTitleStore.cleanTitle(adminTitle).ifEmpty { MemberTitleSettings.DEFAULT_ADMIN_TITLE })
                        .putString(MemberTitleSettings.KEY_MEMBER_TITLE, MemberTitleStore.cleanTitle(memberTitle).ifEmpty { MemberTitleSettings.DEFAULT_MEMBER_TITLE })
                        .putString(MemberTitleSettings.KEY_OWNER_COLOR, MemberTitleStore.cleanColorSpec(ownerColor).ifEmpty { MemberTitleSettings.DEFAULT_OWNER_COLOR })
                        .putString(MemberTitleSettings.KEY_ADMIN_COLOR, MemberTitleStore.cleanColorSpec(adminColor).ifEmpty { MemberTitleSettings.DEFAULT_ADMIN_COLOR })
                        .putString(MemberTitleSettings.KEY_MEMBER_COLOR, MemberTitleStore.cleanColorSpec(memberColor).ifEmpty { MemberTitleSettings.DEFAULT_MEMBER_COLOR })
                        .putString(MemberTitleSettings.KEY_CUSTOM_COLOR, MemberTitleStore.cleanColorSpec(customColor).ifEmpty { MemberTitleSettings.DEFAULT_CUSTOM_COLOR })
                        .putString(MemberTitleSettings.KEY_OWNER_TEXT_COLOR, MemberTitleStore.cleanColorSpec(ownerTextColor).ifEmpty { MemberTitleSettings.DEFAULT_TEXT_COLOR })
                        .putString(MemberTitleSettings.KEY_ADMIN_TEXT_COLOR, MemberTitleStore.cleanColorSpec(adminTextColor).ifEmpty { MemberTitleSettings.DEFAULT_TEXT_COLOR })
                        .putString(MemberTitleSettings.KEY_MEMBER_TEXT_COLOR, MemberTitleStore.cleanColorSpec(memberTextColor).ifEmpty { MemberTitleSettings.DEFAULT_TEXT_COLOR })
                        .putString(MemberTitleSettings.KEY_CUSTOM_TEXT_COLOR, MemberTitleStore.cleanColorSpec(customTextColor).ifEmpty { MemberTitleSettings.DEFAULT_TEXT_COLOR })
                        .apply()
                    Toast.makeText(context, "设置已保存", Toast.LENGTH_SHORT).show()
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
            item { SmallTitle(text = "基础") }
            item {
                SettingsCard {
                    SwitchRow(sp, MemberTitleSettings.KEY_ENABLE, "群员头衔", "在群聊昵称左侧显示身份头衔", MemberTitleSettings.DEFAULT_ENABLE)
                    InsetDivider()
                    SwitchRow(showMember, "显示普通群员", "关闭后只显示群主、管理员和自定义头衔") {
                        showMember = it
                        sp.edit().putBoolean(MemberTitleSettings.KEY_SHOW_MEMBER, it).apply()
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "名称") }
            item {
                SettingsCard {
                    InputRow("群主名称", "最多 8 个字符", ownerTitle) { ownerTitle = it.take(8) }
                    InsetDivider()
                    InputRow("管理员名称", "最多 8 个字符", adminTitle) { adminTitle = it.take(8) }
                    if (showMember) {
                        InsetDivider()
                        InputRow("群员名称", "最多 8 个字符", memberTitle) { memberTitle = it.take(8) }
                    }
                }
            }
            item { SmallTitle(modifier = Modifier.padding(top = 10.dp), text = "颜色") }
            item {
                SettingsCard {
                    ColorPickerRow("群主颜色", "默认黄色，渐变用 #F59E0B,#FDE047", ownerColor, onReset = { ownerColor = MemberTitleSettings.DEFAULT_OWNER_COLOR }) { ownerColor = it.take(19) }
                    InsetDivider()
                    ColorPickerRow("群主文字颜色", "默认白色，支持渐变", ownerTextColor, onReset = { ownerTextColor = MemberTitleSettings.DEFAULT_TEXT_COLOR }) { ownerTextColor = it.take(19) }
                    InsetDivider()
                    ColorPickerRow("管理员颜色", "默认绿色，渐变用 #22C55E,#14B8A6", adminColor, onReset = { adminColor = MemberTitleSettings.DEFAULT_ADMIN_COLOR }) { adminColor = it.take(19) }
                    InsetDivider()
                    ColorPickerRow("管理员文字颜色", "默认白色，支持渐变", adminTextColor, onReset = { adminTextColor = MemberTitleSettings.DEFAULT_TEXT_COLOR }) { adminTextColor = it.take(19) }
                    if (showMember) {
                        InsetDivider()
                        ColorPickerRow("群员颜色", "默认灰色，渐变用 #64748B,#94A3B8", memberColor, onReset = { memberColor = MemberTitleSettings.DEFAULT_MEMBER_COLOR }) { memberColor = it.take(19) }
                        InsetDivider()
                        ColorPickerRow("群员文字颜色", "默认白色，支持渐变", memberTextColor, onReset = { memberTextColor = MemberTitleSettings.DEFAULT_TEXT_COLOR }) { memberTextColor = it.take(19) }
                    }
                    InsetDivider()
                    ColorPickerRow("自定义默认色", "点击聊天头衔可单独设置每个人的名称和颜色，支持渐变", customColor, onReset = { customColor = MemberTitleSettings.DEFAULT_CUSTOM_COLOR }) { customColor = it.take(19) }
                    InsetDivider()
                    ColorPickerRow("自定义文字默认色", "单人未设置文字颜色时使用，支持渐变", customTextColor, onReset = { customTextColor = MemberTitleSettings.DEFAULT_TEXT_COLOR }) { customTextColor = it.take(19) }
                }
            }
        }
    }
}

