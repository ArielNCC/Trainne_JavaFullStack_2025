// VS Code Portfolio Simulator - JavaScript

$(document).ready(function() {
    
    // Initialize the IDE
    initializeIDE();
    
    // File and tab management
    function initializeIDE() {
        // Set default active file
        showFileContent('inicio');
        
        // Activity bar click handlers
        $('.activity-icon').on('click', function() {
            const panel = $(this).data('panel');
            $('.activity-icon').removeClass('active');
            $(this).addClass('active');
            
            // Simulate panel switching (all use explorer for now)
            showPanel(panel);
        });
        
        // File click handlers
        $('.file').on('click', function() {
            const fileId = $(this).data('file');
            
            // Update file explorer
            $('.file').removeClass('active');
            $(this).addClass('active');
            
            // Show tab and content
            showTab(fileId);
            showFileContent(fileId);
        });
        
        // Tab click handlers
        $('.tab').on('click', function() {
            const fileId = $(this).data('file');
            
            // Update tabs
            $('.tab').removeClass('active');
            $(this).addClass('active');
            
            // Update file explorer
            $('.file').removeClass('active');
            $(`.file[data-file="${fileId}"]`).addClass('active');
            
            // Show content
            showFileContent(fileId);
        });
        
        // Tab close handlers
        $('.tab-close').on('click', function(e) {
            e.stopPropagation();
            const tab = $(this).closest('.tab');
            const fileId = tab.data('file');
            
            // Don't close if it's the only tab
            if ($('.tab').length <= 1) {
                return;
            }
            
            closeTab(fileId);
        });
        
        // Window controls
        $('.control.close').on('click', function() {
            if (confirm('¿Estás seguro de que quieres cerrar el portfolio?')) {
                window.close();
            }
        });
        
        $('.control.minimize').on('click', function() {
            // Simulate minimize animation
            $('body').css('transform', 'scale(0.1)');
            setTimeout(() => {
                $('body').css('transform', 'scale(1)');
            }, 500);
        });
        
        $('.control.maximize').on('click', function() {
            // Toggle fullscreen simulation
            if ($('.ide-container').hasClass('maximized')) {
                $('.ide-container').removeClass('maximized');
            } else {
                $('.ide-container').addClass('maximized');
            }
        });
        
        // Terminal simulation
        initializeTerminal();
    }
    
    function showPanel(panelType) {
        // For now, all panels show the same explorer
        // In a real implementation, each panel would have different content
        $('#explorer-panel').show();
    }
    
    function showTab(fileId) {
        // Show tab if not already visible
        const tab = $(`.tab[data-file="${fileId}"]`);
        if (tab.length === 0) {
            return; // Tab doesn't exist
        }
        
        tab.show();
    }
    
    function showFileContent(fileId) {
        // Hide all content
        $('.file-content').removeClass('active');
        
        // Show selected content
        $(`#${fileId}-content`).addClass('active');
        
        // Update line numbers based on content
        updateLineNumbers(fileId);
        
        // Simulate typing effect for first load
        if (!$(`#${fileId}-content`).hasClass('loaded')) {
            simulateTyping(fileId);
            $(`#${fileId}-content`).addClass('loaded');
        }
    }
    
    function closeTab(fileId) {
        const tab = $(`.tab[data-file="${fileId}"]`);
        const isActive = tab.hasClass('active');
        
        // Remove tab
        tab.remove();
        
        // If closed tab was active, activate another tab
        if (isActive) {
            const nextTab = $('.tab').first();
            if (nextTab.length > 0) {
                const nextFileId = nextTab.data('file');
                nextTab.addClass('active');
                $('.file').removeClass('active');
                $(`.file[data-file="${nextFileId}"]`).addClass('active');
                showFileContent(nextFileId);
            }
        }
    }
    
    function updateLineNumbers(fileId) {
        const content = $(`#${fileId}-content`);
        const lines = content.find('.code-line').length;
        const lineNumbers = content.find('.line-numbers');
        
        // Clear existing line numbers
        lineNumbers.empty();
        
        // Add line numbers
        for (let i = 1; i <= lines; i++) {
            lineNumbers.append(`<div class="line-number">${i}</div>`);
        }
    }
    
    function simulateTyping(fileId) {
        const content = $(`#${fileId}-content .code-content`);
        const lines = content.find('.code-line');
        
        // Hide all lines initially
        lines.hide();
        
        // Show lines one by one with delay
        lines.each(function(index) {
            $(this).delay(index * 100).fadeIn(200);
        });
    }
    
    function initializeTerminal() {
        // Simulate terminal cursor blinking
        setInterval(() => {
            $('.cursor').toggle();
        }, 500);
        
        // Terminal input simulation
        let terminalCommands = [
            'npm start',
            'git status',
            'code .',
            'node server.js'
        ];
        
        $('.terminal-content').on('click', function() {
            // Simulate typing in terminal
            const randomCommand = terminalCommands[Math.floor(Math.random() * terminalCommands.length)];
            simulateTerminalInput(randomCommand);
        });
    }
    
    function simulateTerminalInput(command) {
        const terminalContent = $('.terminal-content');
        const lastLine = terminalContent.find('.terminal-line').last();
        const cursor = lastLine.find('.cursor');
        
        // Hide cursor temporarily
        cursor.hide();
        
        // Add command character by character
        let i = 0;
        const typeInterval = setInterval(() => {
            if (i < command.length) {
                cursor.before(command[i]);
                i++;
            } else {
                clearInterval(typeInterval);
                
                // Add new line with response
                setTimeout(() => {
                    addTerminalResponse(command);
                    cursor.show();
                }, 500);
            }
        }, 50);
    }
    
    function addTerminalResponse(command) {
        const terminalContent = $('.terminal-content');
        let response = '';
        
        switch(command) {
            case 'npm start':
                response = 'Portfolio started successfully on port 3000';
                break;
            case 'git status':
                response = 'On branch main\nYour branch is up to date';
                break;
            case 'code .':
                response = 'Opening VS Code...';
                break;
            case 'node server.js':
                response = 'Server running on http://localhost:8080';
                break;
            default:
                response = 'Command executed successfully';
        }
        
        // Add response
        terminalContent.append(`
            <div class="terminal-line">
                <span class="output-text">${response}</span>
            </div>
            <div class="terminal-line">
                <span class="prompt">C:\\ariel\\portfolio></span> <span class="cursor">|</span>
            </div>
        `);
        
        // Scroll to bottom
        terminalContent.scrollTop(terminalContent[0].scrollHeight);
    }
    
    // Keyboard shortcuts
    $(document).on('keydown', function(e) {
        // Ctrl+` to toggle terminal
        if (e.ctrlKey && e.key === '`') {
            e.preventDefault();
            toggleTerminal();
        }
        
        // Ctrl+W to close tab
        if (e.ctrlKey && e.key === 'w') {
            e.preventDefault();
            const activeTab = $('.tab.active');
            if (activeTab.length > 0 && $('.tab').length > 1) {
                const fileId = activeTab.data('file');
                closeTab(fileId);
            }
        }
        
        // Ctrl+Tab to switch tabs
        if (e.ctrlKey && e.key === 'Tab') {
            e.preventDefault();
            switchToNextTab();
        }
        
        // Ctrl+1-5 for direct file access
        if (e.ctrlKey && e.key >= '1' && e.key <= '5') {
            e.preventDefault();
            const fileIndex = parseInt(e.key) - 1;
            const files = ['inicio', 'github', 'linkedin', 'technologies', 'about'];
            if (files[fileIndex]) {
                showFileContent(files[fileIndex]);
                $(`.tab[data-file="${files[fileIndex]}"]`).click();
            }
        }
    });
    
    function toggleTerminal() {
        const terminal = $('.terminal');
        if (terminal.is(':visible')) {
            terminal.slideUp(200);
        } else {
            terminal.slideDown(200);
        }
    }
    
    function switchToNextTab() {
        const currentTab = $('.tab.active');
        let nextTab = currentTab.next('.tab');
        
        if (nextTab.length === 0) {
            nextTab = $('.tab').first();
        }
        
        nextTab.click();
    }
    
    // File explorer expand/collapse
    $('.folder-header').on('click', function() {
        const folder = $(this).closest('.folder');
        const content = folder.find('.folder-content');
        const chevron = $(this).find('.fa-chevron-down, .fa-chevron-right');
        
        if (content.is(':visible')) {
            content.slideUp(200);
            chevron.removeClass('fa-chevron-down').addClass('fa-chevron-right');
        } else {
            content.slideDown(200);
            chevron.removeClass('fa-chevron-right').addClass('fa-chevron-down');
        }
    });
    
    // Syntax highlighting animation
    function animateCodeHighlighting() {
        $('.code-line').each(function(index) {
            const line = $(this);
            setTimeout(() => {
                line.addClass('highlighted');
            }, index * 50);
        });
    }
    
    // Status bar simulation
    function createStatusBar() {
        const statusBar = $(`
            <div class="status-bar">
                <div class="status-left">
                    <span class="status-item">
                        <i class="fas fa-check-circle"></i> Portfolio Ready
                    </span>
                    <span class="status-item">
                        <i class="fab fa-git-alt"></i> main
                    </span>
                </div>
                <div class="status-right">
                    <span class="status-item">Nicolas Ariel</span>
                    <span class="status-item">JavaScript</span>
                    <span class="status-item">UTF-8</span>
                    <span class="status-item">Ln 1, Col 1</span>
                </div>
            </div>
        `);
        
        $('.terminal').after(statusBar);
    }
    
    // Initialize additional features
    setTimeout(() => {
        createStatusBar();
        animateCodeHighlighting();
    }, 1000);
    
    // Add loading effect
    $('body').addClass('loading');
    setTimeout(() => {
        $('body').removeClass('loading').addClass('loaded');
    }, 500);
    
    // Theme switching (bonus feature)
    function switchTheme(theme) {
        const themes = {
            dark: {
                background: '#1e1e1e',
                foreground: '#d4d4d4',
                accent: '#007acc'
            },
            light: {
                background: '#ffffff',
                foreground: '#333333',
                accent: '#0066cc'
            }
        };
        
        if (themes[theme]) {
            $(':root').css({
                '--bg-color': themes[theme].background,
                '--text-color': themes[theme].foreground,
                '--accent-color': themes[theme].accent
            });
        }
    }
    
    // Add theme switcher to title bar
    $('.title-bar-right').append(`
        <i class="fas fa-palette theme-switcher" title="Cambiar tema"></i>
    `);
    
    $('.theme-switcher').on('click', function() {
        const currentTheme = $('body').hasClass('light-theme') ? 'dark' : 'light';
        $('body').toggleClass('light-theme');
        switchTheme(currentTheme);
    });
    
    // Console log for debugging
    console.log('🚀 VS Code Portfolio Simulator initialized');
    console.log('💡 Tips:');
    console.log('   - Click on files in the explorer to switch');
    console.log('   - Use Ctrl+` to toggle terminal');
    console.log('   - Use Ctrl+W to close tabs');
    console.log('   - Use Ctrl+1-5 for quick file access');
});

// Additional utility functions
function logPortfolioInfo() {
    console.log(`
    ╔══════════════════════════════════════╗
    ║          PORTFOLIO SIMULATOR         ║
    ║            Nicolas Ariel             ║
    ║      Backend Developer en formación  ║
    ╠══════════════════════════════════════╣
    ║ 📧 micorreo@midireccion.cl          ║
    ║ 📱 +56 9 123456789                  ║
    ║ 📍 Santiago - Chile                  ║
    ║ 💼 Abierto a oportunidades          ║
    ╚══════════════════════════════════════╝
    `);
}

// Initialize additional features
$(window).on('load', function() {
    logPortfolioInfo();
    
    // Add fade-in animation to all elements
    $('.ide-container').addClass('fade-in');
    
    // Simulate realistic loading time
    setTimeout(() => {
        $('.file-content.active').addClass('syntax-loaded');
    }, 1500);
});

// Handle window resize
$(window).on('resize', function() {
    // Adjust layout for mobile
    if ($(window).width() < 768) {
        $('.sidebar-panel').addClass('mobile-collapsed');
    } else {
        $('.sidebar-panel').removeClass('mobile-collapsed');
    }
});

// Handle page visibility change
$(document).on('visibilitychange', function() {
    if (document.hidden) {
        console.log('👋 ¡Gracias por visitar mi portfolio!');
    } else {
        console.log('🎉 ¡Bienvenido de vuelta!');
    }
});

// Easter egg: Konami code
let konamiCode = [];
const konamiSequence = [38, 38, 40, 40, 37, 39, 37, 39, 66, 65]; // ↑↑↓↓←→←→BA

$(document).on('keydown', function(e) {
    konamiCode.push(e.keyCode);
    
    if (konamiCode.length > konamiSequence.length) {
        konamiCode.shift();
    }
    
    if (konamiCode.join('') === konamiSequence.join('')) {
        showEasterEgg();
        konamiCode = [];
    }
});

function showEasterEgg() {
    const easter = $(`
        <div class="easter-egg">
            <div class="easter-content">
                <h3>🎉 ¡Easter Egg Encontrado! 🎉</h3>
                <p>¡Felicidades! Has encontrado el código secreto.</p>
                <p>Esto demuestra tu curiosidad y atención al detalle.</p>
                <p>¡Exactamente lo que busco en un colaborador!</p>
                <button class="btn btn-primary" onclick="$(this).closest('.easter-egg').fadeOut()">
                    ¡Genial!
                </button>
            </div>
        </div>
    `);
    
    $('body').append(easter);
    easter.fadeIn();
}
